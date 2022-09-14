package com.waveinformatica.demo.services;

import com.waveinformatica.demo.dto.EditableMarketDTO;
import com.waveinformatica.demo.dto.MarketDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Primary
@Slf4j
public class DBMarketService implements MarketService {

    @Autowired
    private DataSource ds;

    @Override
    public Collection<MarketDTO> listMarkets() {
        return findMarkets(null);
    }

    @Override
    public Collection<MarketDTO> findMarkets(String prefix) {
        final List<MarketDTO> markets = new ArrayList<>();
        try (Connection conn = ds.getConnection()) {
            String sql = "select code,name,area from markets" + (prefix == null ? "" : " where name like ?");
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                if (prefix != null) {
                    stmt.setString(1, prefix + "%");
                }

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        MarketDTO m = new MarketDTO();
                        m.setId(rs.getLong("code"));
                        m.setName(rs.getString("name"));
                        m.setArea(rs.getString("area"));
                        markets.add(m);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return markets;
    }

    @Override
    public MarketDTO getMarket(long id) {
        try (Connection conn = ds.getConnection()) {
            String sql = "select code,name,area from markets where code = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        MarketDTO m = new MarketDTO();
                        m.setId(rs.getLong("code"));
                        m.setName(rs.getString("name"));
                        m.setArea(rs.getString("area"));
                        return m;
                    }

                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addMarket(MarketDTO m) {
        try (Connection conn = ds.getConnection()) {
            conn.setAutoCommit(false);
            try {
                String sql = "insert into markets (code,name,area) values (?,?,?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setLong(1, m.getId());
                    stmt.setString(2, m.getName());
                    stmt.setString(3, m.getArea());
                    stmt.executeUpdate();
                }

                conn.commit();
                return true;
            } catch (SQLIntegrityConstraintViolationException e) {
                log.error("Got error inserting market {}: {}", m.getId(), e.getMessage());
                conn.rollback();
                return false;
            } catch (Throwable e) {
                log.error("Got error inserting market {}: {}", m.getId(), e.getMessage());
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteMarket(long id) {
        log.debug("Deleting market {}", id);
        try (Connection conn = ds.getConnection()) {
            conn.setAutoCommit(false);
            try {
                String sql = "delete from markets where code = ?";
                boolean deleted;
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setLong(1, id);
                    deleted = stmt.executeUpdate() != 0;
                }

                conn.commit();
                return deleted;
            } catch (Throwable e) {
                log.error("Got error deleting market {}: {}", id, e.getMessage());
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateMarket(MarketDTO m) {
        log.debug("Updating market {}", m.getId());
        try (Connection conn = ds.getConnection()) {
            conn.setAutoCommit(false);
            try {
                String sql = "update markets set name = ?, area = ? where code = ?";
                boolean updated;
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, m.getName());
                    stmt.setString(2, m.getArea());
                    stmt.setLong(3, m.getId());
                    updated = stmt.executeUpdate() != 0;
                }

                conn.commit();
                return updated;
            } catch (Throwable e) {
                log.error("Got error updating market {}: {}", m.getId(), e.getMessage());
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private <T> void fillUpdateSet(String k, Optional<T> v, final List<String> setStringList, final List<Object> params) {
        if (v != null) {
            setStringList.add(String.format(" %s = ?", k));
            params.add(v.isPresent() ? v.get() : null);
        }
    }

    @Override
    public boolean updateMarket(long id, EditableMarketDTO m) {
        log.debug("Patching market {}", id);
        try (Connection conn = ds.getConnection()) {
            conn.setAutoCommit(false);
            try {
                String sql = "update markets set";
                //aggiungere campi da modificare
                /*
                int c = 0;
                if (m.getName() != null) {
                    sql += " name = ?";
                    c++;
                }

                if (m.getArea() != null) {
                    if (c > 0) {
                        sql += ",";
                    }
                    sql += " area = ?";
                    c++;
                }
                */

                List<String> setStringList = new ArrayList<>();
                List<Object> params = new ArrayList<>();

                fillUpdateSet("name", m.getName(), setStringList, params);
                fillUpdateSet("area", m.getArea(), setStringList, params);

                if (params.isEmpty()) {
                    return true;
                }

                sql += setStringList.stream().collect(Collectors.joining(","));
                //equivalente a sql += Strings.join(setStringList, ',');

                sql += " where code = ?";

                boolean updated;
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    /*
                    c = 0;
                    if (m.getName() != null) {
                        c++;
                        stmt.setString(c, m.getName().isPresent() ? m.getName().get() : null);
                    }
                    
                    if (m.getArea() != null) {
                        c++;
                        stmt.setString(c, m.getArea().isPresent() ? m.getArea().get() : null);
                    }

                    c++;
                    stmt.setLong(c, id);
                    */
                    for (int i = 0; i<params.size(); i++) {
                        stmt.setObject(i+1, params.get(i));
                    }
                    stmt.setLong(params.size() + 1, id);
                    updated = stmt.executeUpdate() != 0;
                }

                conn.commit();
                return updated;
            } catch (Throwable e) {
                log.error("Got error patching market {}: {}", id, e.getMessage());
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
