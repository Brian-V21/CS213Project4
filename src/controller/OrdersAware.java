package src.controller;

import src.model.OrdersManager;

/**
 * Controllers that need access to the shared OrdersManager implement this.
 */
public interface OrdersAware {
    /**
     * @param manager orders manager
     */
    void setOrdersManager(OrdersManager manager);
}
