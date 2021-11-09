package kitchenpos.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kitchenpos.table.domain.OrderTable;
import kitchenpos.table.domain.TableGroup;

import static kitchenpos.fixture.OrderTableFixture.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("TableGroup 단위 테스트")
class TableGroupTest {

    @Test
    @DisplayName("테이블 그룹이 올바르다면 그룹을 생성할 수 있다.")
    void create() {
        // when & then
        assertDoesNotThrow(() -> new TableGroup(Arrays.asList(단일_손님0_테이블1, 단일_손님0_테이블2)));
    }

    @Test
    @DisplayName("테이블 그룹에 포함하려는 테이블 목록이 둘 미만이면 그룹을 생성할 수 없다.")
    void lessThanTwo() {
        // when & then
        assertThatThrownBy(() -> new TableGroup(Collections.singletonList(단일_손님0_테이블1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("그룹을 지정하려면 둘 이상의 테이블이 필요합니다.");
    }

    @Test
    @DisplayName("테이블이 비어있지 않으면 그룹으로 지정할 수 없습니다.")
    void notEmptyTable() {
        // when & then
        assertThatThrownBy(() -> new TableGroup(Arrays.asList(단일_손님0_테이블1, 단일_손님2_테이블)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("테이블이 비어있지 않거나 이미 다른 그룹에 속한 테이블은 그룹으로 지정할 수 없습니다.");
    }

    @Test
    @DisplayName("테이블이 이미 다른 그룹에 속한 테이블은 그룹으로 지정할 수 없습니다.")
    void anotherGroupTable() {
        // when & then
        assertThatThrownBy(() -> new TableGroup(Arrays.asList(단일_손님0_테이블1, 그룹_손님0_테이블)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("테이블이 비어있지 않거나 이미 다른 그룹에 속한 테이블은 그룹으로 지정할 수 없습니다.");
    }

    @Test
    @DisplayName("테이블 그룹을  올바르다면 그룹을 생성할 수 있다.")
    void upGroup() {
        // when & then
        assertDoesNotThrow(() -> new TableGroup(Arrays.asList(단일_손님0_테이블1, 단일_손님0_테이블2)));
    }

    @Test
    @DisplayName("그룹에 속한 모든 테이블이 비어있지 않게된다.")
    void changeFull() {
        // given
        TableGroup group = new TableGroup(
                Arrays.asList(
                        new OrderTable(0, true),
                        new OrderTable(0, true),
                        new OrderTable(0, true)
                )
        );

        // when
        group.changeFull();
        List<OrderTable> results = group.getOrderTables();

        // then
        assertFalse(results.get(0).isEmpty());
        assertFalse(results.get(1).isEmpty());
        assertFalse(results.get(2).isEmpty());
    }
}
