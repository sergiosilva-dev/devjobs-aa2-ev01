package com.devjobs.dao.jdbc;

import com.devjobs.config.ConnectionFactory;
import com.devjobs.dao.JobOfferDao;
import com.devjobs.domain.JobOffer;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcJobOfferDao implements JobOfferDao {

    @Override
    public Long create(JobOffer o) {
        final String sql = """
                INSERT INTO job_offers(title, description, min_salary, max_salary, location, company_id)
                VALUES (?,?,?,?,?,?)
                """;
        try (Connection c = ConnectionFactory.get();
                PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, o.getTitle());
            ps.setString(2, o.getDescription());
            ps.setBigDecimal(3, o.getMinSalary());
            ps.setBigDecimal(4, o.getMaxSalary());
            ps.setString(5, o.getLocation());
            ps.setLong(6, o.getCompanyId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getLong(1) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creando JobOffer", e);
        }
    }

    @Override
    public Optional<JobOffer> findById(Long id) {
        final String sql = "SELECT * FROM job_offers WHERE id = ?";
        try (Connection c = ConnectionFactory.get();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error buscando JobOffer por id", e);
        }
    }

    @Override
    public List<JobOffer> findAll(int page, int pageSize) {
        final String sql = """
                SELECT * FROM job_offers
                ORDER BY created_at DESC
                LIMIT ? OFFSET ?
                """;
        try (Connection c = ConnectionFactory.get();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, (page - 1) * pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                List<JobOffer> list = new ArrayList<>();
                while (rs.next())
                    list.add(map(rs));
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error listando JobOffers", e);
        }
    }

    @Override
    public boolean update(JobOffer o) {
        final String sql = """
                UPDATE job_offers
                SET title=?, description=?, min_salary=?, max_salary=?, location=?, company_id=?
                WHERE id=?
                """;
        try (Connection c = ConnectionFactory.get();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, o.getTitle());
            ps.setString(2, o.getDescription());
            ps.setBigDecimal(3, o.getMinSalary());
            ps.setBigDecimal(4, o.getMaxSalary());
            ps.setString(5, o.getLocation());
            ps.setLong(6, o.getCompanyId());
            ps.setLong(7, o.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando JobOffer", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        final String sql = "DELETE FROM job_offers WHERE id = ?";
        try (Connection c = ConnectionFactory.get();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando JobOffer", e);
        }
    }

    private JobOffer map(ResultSet rs) throws SQLException {
        JobOffer o = new JobOffer();
        o.setId(rs.getLong("id"));
        o.setTitle(rs.getString("title"));
        o.setDescription(rs.getString("description"));
        o.setMinSalary(rs.getBigDecimal("min_salary"));
        o.setMaxSalary(rs.getBigDecimal("max_salary"));
        o.setLocation(rs.getString("location"));
        Timestamp ts = rs.getTimestamp("created_at");
        o.setCreatedAt(ts != null ? ts.toLocalDateTime() : LocalDateTime.now());
        o.setCompanyId(rs.getLong("company_id"));
        return o;
    }
}