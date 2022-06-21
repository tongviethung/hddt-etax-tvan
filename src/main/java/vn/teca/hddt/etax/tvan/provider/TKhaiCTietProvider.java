package vn.teca.hddt.etax.tvan.provider;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatementBuilder;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.entity.EntityHelper;
import com.datastax.oss.driver.api.mapper.entity.saving.NullSavingStrategy;
import vn.teca.hddt.etax.tvan.model.TKhaiCTiet;

import java.util.List;

public class TKhaiCTietProvider {
    private final CqlSession session;
    private final EntityHelper<TKhaiCTiet> entityHelper;

    public TKhaiCTietProvider(MapperContext context, EntityHelper<TKhaiCTiet> entityHelper) {
        this.session = context.getSession();
        this.entityHelper = entityHelper;
    }

    public void saveList(List<TKhaiCTiet> items) {
        PreparedStatement saveStatement = session.prepare(entityHelper.insert().build());
        for (TKhaiCTiet item : items) {
            BoundStatementBuilder boundStatementBuilder = saveStatement.boundStatementBuilder();
            entityHelper.set(item, boundStatementBuilder, NullSavingStrategy.DO_NOT_SET);
            session.execute(boundStatementBuilder.build());
        }
    }
}
