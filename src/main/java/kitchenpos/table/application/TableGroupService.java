package kitchenpos.table.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.table.domain.OrderTable;
import kitchenpos.table.domain.OrderTableRepository;
import kitchenpos.table.domain.TableGroup;
import kitchenpos.table.domain.TableGroupRepository;
import kitchenpos.table.ui.request.CreateTableGroupRequest;
import kitchenpos.table.ui.response.TableGroupResponse;

@Service
public class TableGroupService {
    private final OrderTableRepository orderTableRepository;
    private final TableGroupRepository tableGroupRepository;

    public TableGroupService(
            final OrderTableRepository orderTableRepository,
            final TableGroupRepository tableGroupRepository
    ) {
        this.orderTableRepository = orderTableRepository;
        this.tableGroupRepository = tableGroupRepository;
    }

    @Transactional
    public TableGroupResponse create(final CreateTableGroupRequest request) {
        final List<OrderTable> orderTables = request.getOrderTables().stream()
                                                    .map(orderTable -> orderTableRepository.findById(orderTable.getId())
                                                                                           .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 테이블은 그룹으로 지정할 수 없습니다.")))
                                                    .collect(Collectors.toList());

        final TableGroup tableGroup = new TableGroup(orderTables);
        tableGroup.changeFull();

        final TableGroup savedTableGroup = tableGroupRepository.save(tableGroup);
        return TableGroupResponse.from(savedTableGroup);
    }

    @Transactional
    public void ungroup(final Long tableGroupId) {
        final TableGroup tableGroup = tableGroupRepository.findById(tableGroupId)
                                                          .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        tableGroup.ungroup();
    }
}
