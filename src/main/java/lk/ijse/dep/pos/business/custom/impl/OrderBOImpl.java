package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.OrderBO;
import lk.ijse.dep.pos.dao.custom.CustomerDAO;
import lk.ijse.dep.pos.dao.custom.ItemDAO;
import lk.ijse.dep.pos.dao.custom.OrderDAO;
import lk.ijse.dep.pos.dao.custom.OrderDetailDAO;
import lk.ijse.dep.pos.entity.Item;
import lk.ijse.dep.pos.entity.Order;
import lk.ijse.dep.pos.entity.OrderDetail;
import lk.ijse.dep.pos.util.OrderDetailTM;
import lk.ijse.dep.pos.util.OrderTM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Component
@Transactional
public class OrderBOImpl implements OrderBO { // , Temp
    @Autowired
    private OrderDAO orderDAO; // = DAOFactory.getInstance().getDAO(DAOType.ORDER);
    @Autowired
    private OrderDetailDAO orderDetailDAO; // = DAOFactory.getInstance().getDAO(DAOType.ORDER_DETAIL);
    @Autowired
    private ItemDAO itemDAO; // = DAOFactory.getInstance().getDAO(DAOType.ITEM);
    @Autowired
    private CustomerDAO customerDAO; // = DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);

    // Interface through injection
/*    @Override
    public void injection() {
        this.orderDAO = DAOFactory.getInstance().getDAO(DAOType.ORDER);
    }  */

    // Setter method injection
/*    private void setOrderDAO(){
        this.orderDAO = DAOFactory.getInstance().getDAO(DAOType.ORDER);
    }*/

    public String getNewOrderId() throws Exception {

        String lastOrderId = orderDAO.getLastOrderId();

        if (lastOrderId == null) {
            return "OD001";
        } else {
            int maxId = Integer.parseInt(lastOrderId.replace("OD", ""));
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "OD00" + maxId;
            } else if (maxId < 100) {
                id = "OD0" + maxId;
            } else {
                id = "OD" + maxId;
            }
            return id;
        }
    }

    public void placeOrder(OrderTM order, List<OrderDetailTM> orderDetails) throws Exception {

        orderDAO.save(new Order(order.getOrderId(),
                Date.valueOf(order.getOrderDate()),
                customerDAO.find(order.getCustomerId())));

        for (OrderDetailTM orderDetail : orderDetails) {
            orderDetailDAO.save(new OrderDetail(
                    order.getOrderId(), orderDetail.getCode(),
                    orderDetail.getQty(), BigDecimal.valueOf(orderDetail.getUnitPrice())
            ));

            Item item = itemDAO.find(orderDetail.getCode());
            item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());
        }
    }
}
