package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.CrudDAOImpl;
import lk.ijse.dep.pos.dao.custom.OrderDAO;
import lk.ijse.dep.pos.entity.Order;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class OrderDAOImpl extends CrudDAOImpl<Order, String > implements OrderDAO {


    public String getLastOrderId() throws Exception {
        List list =  getSession().createQuery("SELECT o.id FROM Order o ORDER BY o.id DESC")
                .setMaxResults(1).list();
        return (list.size()> 0)? (String) list.get(0): null;
    }
}
