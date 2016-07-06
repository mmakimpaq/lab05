package savings.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import common.math.Percentage;
import org.springframework.stereotype.Repository;
import savings.model.Merchant;
import savings.repository.MerchantRepository;

// TODO #1 mark as repository component
@Repository
public class JdbcMerchantRepository implements MerchantRepository {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcMerchantRepository.class);

    private DataSource dataSource;

    private final Map<String, Merchant> cache = new HashMap<>();

    // TODO #2 use constructor dependency injection instead
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    void populateCache() {
        LOG.info("Populating merchants cache...");
        String sql =
                "select * " +
                "from MERCHANT";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Merchant merchant = readMerchantFrom(resultSet);
                    cache.put(merchant.getNumber(), merchant);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error in findByNumber!", e);
        }
    }

    @PreDestroy
    void clearCache() {
        LOG.info("Clearing merchants cache...");
        cache.clear();
    }

    @Override
    public Merchant findByNumber(String number) {
        Merchant merchant = cache.get(number);
        if (merchant == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return merchant;
    }

    private Merchant readMerchantFrom(ResultSet resultSet) throws SQLException {
        Merchant account = new Merchant(
                resultSet.getString("NUMBER"),
                resultSet.getString("NAME"),
                new Percentage(resultSet.getBigDecimal("PAYBACK")),
                PaybackPolices.valueOf(resultSet.getString("PAYBACK_POLICY").toUpperCase()));
        account.setId(resultSet.getLong("ID"));
        return account;
    }
}
