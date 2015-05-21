package io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase;

import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderSearchCriteriaTo;

import java.util.List;

/**
 * Interface of {@link io.oasp.gastronomy.restaurant.general.logic.base.AbstractUc use case} to get specific or all
 * {@link OrderEto orders}.
 *
 * @author mvielsac
 */
public interface UcFindOrder {

  /** @see net.sf.mmm.util.component.api.Cdi#CDI_NAME */
  String CDI_NAME = "UcFindOrder";

  /**
   * @param criteria the {@link OrderSearchCriteriaTo}.
   * @return the {@link List} of matching {@link OrderEto}s.
   */
  List<OrderEto> findOrderEtos(OrderSearchCriteriaTo criteria);

  /**
   * @param criteria the {@link OrderSearchCriteriaTo}.
   * @return the {@link List} of matching {@link OrderCto}s.
   */
  List<OrderCto> findOrderCtos(OrderSearchCriteriaTo criteria);

  /**
   * @param order the {@link OrderEto}.
   * @return the corresponding {@link OrderCto} (order with order-positions).
   */
  OrderCto findOrderCto(OrderEto order);

  /**
   * This method returns an {@link OrderEto order}.
   *
   * @param orderId identifier of the searched {@link OrderEto order}
   * @return the {@link OrderEto order} with the given identifier. Will be <code>null</code> if the {@link OrderEto
   *         order} does not exist.
   */
  OrderEto findOrder(Long orderId);

  /**
   * This method returns a the {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState#OPEN
   * open} {@link OrderEto order} for the specified table.
   *
   * @param tableId the {@link io.oasp.gastronomy.restaurant.tablemanagement.common.api.Table#getId() table ID} the
   *        requested order shall be {@link OrderEto#getTableId() associated} with.
   * @return the {@link OrderEto order} {@link OrderEto#getTableId() associated} with the given <code>tableId</code> in
   *         {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState#OPEN open}
   *         {@link OrderEto#getState() state} or <code>null</code> if no such {@link OrderEto order} exists.
   */
  OrderEto findOpenOrderForTable(Long tableId);

}
