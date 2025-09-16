package com.devjobs;

import com.devjobs.dao.JobOfferDao;
import com.devjobs.dao.jdbc.JdbcJobOfferDao;
import com.devjobs.domain.JobOffer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class App {
  private static final Scanner in = new Scanner(System.in);
  private static final JobOfferDao dao = new JdbcJobOfferDao();

  public static void main(String[] args) {
    System.out.println("DevJobs AA2-EV01 - CRUD JDBC\n");
    System.out.println("Ping DB: " + com.devjobs.config.ConnectionFactory.ping());
    while (true) {
      System.out.println("""
          Menú
          1) Listar ofertas
          2) Crear oferta
          3) Buscar por ID
          4) Actualizar título
          5) Eliminar por ID
          0) Salir
          """);
      System.out.print("Opción: ");
      String opt = in.nextLine().trim();
      switch (opt) {
        case "1" -> list();
        case "2" -> create();
        case "3" -> find();
        case "4" -> updateTitle();
        case "5" -> delete();
        case "0" -> {
          System.out.println("CRUD de Job Offers funcionando correctamente");
          System.out.println("¡Gracias por usar DevJobs!"); 
          return;
        }
        default -> System.out.println("Opción inválida.");
      }
    }
  }

  private static void list() {
    System.out.print("Página (1..n): ");
    int page = Integer.parseInt(in.nextLine().trim());
    List<JobOffer> rows = dao.findAll(page, 10);
    if (rows.isEmpty())
      System.out.println("Sin registros.");
    else
      rows.forEach(System.out::println);
  }

  private static void create() {
    System.out.print("Título: ");
    String title = in.nextLine();
    System.out.print("Descripción: ");
    String description = in.nextLine();
    System.out.print("Salario mínimo: ");
    BigDecimal min = new BigDecimal(in.nextLine());
    System.out.print("Salario máximo: ");
    BigDecimal max = new BigDecimal(in.nextLine());
    System.out.print("Ubicación: ");
    String location = in.nextLine();
    System.out.print("CompanyId (existe en tabla companies): ");
    Long companyId = Long.parseLong(in.nextLine());

    JobOffer o = new JobOffer(null, title, description, min, max, location, null, companyId);
    Long id = dao.create(o);
    System.out.println("Creado id=" + id);
  }

  private static void find() {
    System.out.print("ID: ");
    Long id = Long.parseLong(in.nextLine());
    Optional<JobOffer> o = dao.findById(id);
    System.out.println(o.orElse(null));
  }

  private static void updateTitle() {
    System.out.print("ID a actualizar: ");
    Long id = Long.parseLong(in.nextLine());
    Optional<JobOffer> opt = dao.findById(id);
    if (opt.isEmpty()) {
      System.out.println("No existe");
      return;
    }
    System.out.print("Nuevo título: ");
    String newTitle = in.nextLine();
    JobOffer o = opt.get();
    o.setTitle(newTitle);
    System.out.println(dao.update(o) ? "Actualizado" : "No actualizado");
  }

  private static void delete() {
    System.out.print("ID a eliminar: ");
    Long id = Long.parseLong(in.nextLine());
    System.out.println(dao.delete(id) ? "Eliminado" : "No existe");
  }
}