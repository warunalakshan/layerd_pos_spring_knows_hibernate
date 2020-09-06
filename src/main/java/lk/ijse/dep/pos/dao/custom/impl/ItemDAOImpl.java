package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.CrudDAOImpl;
import lk.ijse.dep.pos.dao.custom.ItemDAO;
import lk.ijse.dep.pos.entity.Item;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ItemDAOImpl extends CrudDAOImpl<Item, String> implements ItemDAO {

    public String getLastItemCode() throws Exception {
        List list = getSession().createQuery("SELECT i.code FROM lk.ijse.dep.pos.entity.Item i ORDER BY i.code DESC")
                .setMaxResults(1).list();
        return list.size() > 0 ? (String) list.get(0) : null;
    }

}
