package Daos;

import Beans.Department;
import Beans.Employee;
import Beans.Location;
import Dtos.SalarioPorDepartamentoDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DepartmentDao extends DaoBase {

    public ArrayList<Department> listaDepartamentos() {
        ArrayList<Department> listaDepartamentos = new ArrayList<>();

        try (Connection conn = this.getConection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM departments d " +
                     "left join employees e on d.manager_id = e.employee_id " +
                     "left join locations l on d.location_id = l.location_id");) {

            while (rs.next()) {
                Department department = new Department();
                department = parseResultSet(rs);

                listaDepartamentos.add(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDepartamentos;
    }

    public Department obtener(int departmentId) {

        Department department = null;

        String sql = "SELECT * FROM departments d " +
                "left join employees e on d.manager_id = e.employee_id " +
                "left join locations l on d.location_id = l.location_id " +
                "WHERE d.department_id = ?";
        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, departmentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    department = parseResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return department;
    }

    private Department parseResultSet(ResultSet rs) {
        Department department = new Department();
        try {
            department.setDepartmentId(rs.getInt(1));
            department.setDepartmentName(rs.getString(2));

            if (rs.getInt("d.manager_id") != 0) {
                Employee manager = new Employee();
                manager.setEmployeeId(rs.getInt("employee_id"));
                manager.setFirstName(rs.getString("first_name"));
                manager.setLastName(rs.getString("last_name"));
                department.setManager(manager);
            }

            if(rs.getInt("l.location_id") != 0){
                Location location = new Location();
                location.setLocationId(rs.getInt("l.location_id"));
                location.setCity(rs.getString("city"));
                department.setLocation(location);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return department;
    }

    public void crear(int departmentId, String departmentName, int managerId, int locationId) {

        try (Connection conn = this.getConection();) {
            String sql = "INSERT INTO departments (`department_id`, `department_name`, `manager_id`, `location_id`) "
                    + "VALUES (?,?,?,?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, departmentId);
                pstmt.setString(2, departmentName);

                if (managerId == 0) {
                    pstmt.setNull(3, Types.INTEGER);
                } else {
                    pstmt.setInt(3, managerId);
                }

                if (locationId == 0) {
                    pstmt.setNull(4, Types.INTEGER);
                } else {
                    pstmt.setInt(4, locationId);
                }

                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void actualizar(int departmentId, String departmentName, int managerId, int locationId) {

        try (Connection conn = this.getConection();) {
            String sql = "UPDATE departments SET department_name = ?, manager_id = ?, location_id = ? "
                    + "WHERE department_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, departmentName);

                if (managerId == 0) {
                    pstmt.setNull(2, Types.INTEGER);
                } else {
                    pstmt.setInt(2, managerId);
                }

                if (locationId == 0) {
                    pstmt.setNull(3, Types.INTEGER);
                } else {
                    pstmt.setInt(3, locationId);
                }

                pstmt.setInt(4,departmentId);

                pstmt.executeUpdate();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void borrar(int departmentId) {

        try (Connection conn = this.getConection();) {
            String sql = "DELETE FROM departments WHERE department_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, departmentId);
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<SalarioPorDepartamentoDto> listaSalarioPorDepartamento() {

        ArrayList<SalarioPorDepartamentoDto> lista = new ArrayList<>();

        String sql = "select department_name, min(salary), max(salary), truncate(avg(salary),2) "
                + "from departments d "
                + "inner join employees e on e.department_id = d.department_id "
                + "group by d.department_name order by d.department_name";

        try (Connection conn = this.getConection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                SalarioPorDepartamentoDto dto = new SalarioPorDepartamentoDto();
                dto.setNombreDepartamento(rs.getString(1));
                dto.setSalarioMinimo(rs.getBigDecimal(2));
                dto.setSalarioMaximo(rs.getBigDecimal(3));
                dto.setSalarioPromedio(rs.getBigDecimal(4));
                lista.add(dto);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

}
