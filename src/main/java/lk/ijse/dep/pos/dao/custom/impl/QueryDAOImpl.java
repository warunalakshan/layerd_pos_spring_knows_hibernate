package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.custom.QueryDAO;
import lk.ijse.dep.pos.entity.CustomEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryDAOImpl implements QueryDAO {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public CustomEntity getOrderDetail(String orderId) throws Exception {
        return (CustomEntity) getSession().createNativeQuery("SELECT  o.id AS orderId," +
                " c.name AS customerName, o.date AS orderDate FROM `Order` o\n" +
                "INNER JOIN Customer c on o.customerId = c.id\n" +
                "WHERE o.id=?1").setParameter(1, orderId).
                setResultTransformer(Transformers.aliasToBean(CustomEntity.class)).uniqueResult();

    }

    @Override
    public CustomEntity getOrderDetail2(String orderId) throws Exception {
        return (CustomEntity) getSession().createQuery("SELECT NEW lk.ijse.dep.pos.entity.CustomEntity(C.id, C.name, O.id) " +
                "FROM Order O INNER JOIN O.customer C WHERE O.id=:id")
                .setParameter("id", orderId).uniqueResult();
    }

    @Override
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
