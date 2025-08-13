/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package br.edu.ifpr;

import br.edu.ifpr.dao.ServiceDAO;
import br.edu.ifpr.dao.SchedulingDAO;
import br.edu.ifpr.dao.VehicleDAO;
import br.edu.ifpr.dao.WorkshopDAO;

import br.edu.ifpr.model.entity.Service;
import br.edu.ifpr.model.entity.Scheduling;
import br.edu.ifpr.model.entity.Vehicle;
import br.edu.ifpr.model.entity.Workshop;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


/**
 *
 * @author Aluno
 */
public class SchedulingSystem {

    public static void main(String[] args) {
        // DAOs (seu padrão)
        WorkshopDAO workshopDAO = new WorkshopDAO();
        VehicleDAO vehicleDAO = new VehicleDAO();
        ServiceDAO serviceDAO = new ServiceDAO();
        SchedulingDAO schedulingDAO = new SchedulingDAO();

        try {
            System.out.println("== Iniciando CRUD de Workshop ==");
            Workshop w = new Workshop();
            w.setName("Oficina Central");
            w.setVacanciesDay(6);
            workshopDAO.create(w);
            System.out.println("Workshop criado com id: " + w.getId());

            Workshop wFound = workshopDAO.retrieve(w.getId());
            System.out.println("Workshop recuperado: " + wFound.getName() + " (vagas/dia=" + wFound.getVacanciesDay() + ")");

            wFound.setVacanciesDay(7);
            workshopDAO.update(wFound);
            System.out.println("Workshop atualizado para vagas/dia=" + workshopDAO.retrieve(w.getId()).getVacanciesDay());

            List<Workshop> allW = workshopDAO.findAll();
            System.out.println("Total de workshops: " + allW.size());

            System.out.println("\n== Iniciando CRUD de Vehicle ==");
            Vehicle v = new Vehicle();
            v.setLicensePlate("ABC1D23"); // Ajuste se sua validação aceitar outro formato
            v.setModel("Gol 1.0");
            vehicleDAO.create(v);
            System.out.println("Vehicle criado com id: " + v.getId());

            Vehicle vFound = vehicleDAO.retrieve(v.getId());
            System.out.println("Vehicle recuperado: placa=" + vFound.getLicensePlate() + ", modelo=" + vFound.getModel());

            vFound.setModel("Gol 1.0 Flex");
            vehicleDAO.update(vFound);
            System.out.println("Vehicle atualizado: modelo=" + vehicleDAO.retrieve(v.getId()).getModel());

            List<Vehicle> allV = vehicleDAO.findAll();
            System.out.println("Total de vehicles: " + allV.size());

            System.out.println("\n== Iniciando CRUD de Service ==");
            Service s = new Service();
            s.setDescription("Troca de óleo");
            s.setDurationMin(60); // dentro da regra (<= 240)
            // se você tiver enum category e for obrigatório, set aqui: s.setCategory(ServiceCategory.TROCA_OLEO);
            serviceDAO.create(s);
            System.out.println("Service criado com id: " + s.getId());

            Service sFound = serviceDAO.retrieve(s.getId());
            System.out.println("Service recuperado: " + sFound.getDescription() + " (" + sFound.getDurationMin() + "min)");

            sFound.setDescription("Troca de óleo + filtro");
            serviceDAO.update(sFound);
            System.out.println("Service atualizado: " + serviceDAO.retrieve(s.getId()).getDescription());

            List<Service> allS = serviceDAO.findAll();
            System.out.println("Total de services: " + allS.size());

            System.out.println("\n== Iniciando CRUD de Scheduling ==");
            Scheduling sch = new Scheduling();
            sch.setWorkshop(wFound);
            sch.setVehicle(vFound);
            sch.setService(sFound);

            // agenda para o próximo dia útil às 10:00 (simples, sem validações de regra aqui)
            LocalDate nextBusinessDay = nextBusinessDay(LocalDate.now());
            LocalDateTime start = LocalDateTime.of(nextBusinessDay, LocalTime.of(10, 0));
            sch.setStartTime(start);

            // endTime será calculado pelo @PrePersist/@PreUpdate no entity
            schedulingDAO.create(sch);
            System.out.println("Scheduling criado com id: " + sch.getId());

            Scheduling schFound = schedulingDAO.retrieve(sch.getId());
            System.out.println("Scheduling recuperado: start=" + schFound.getStartTime() + " | end=" + schFound.getEndTime());

            // Atualiza para outro horário, só para exercitar o update + recomputar endTime
            schFound.setStartTime(start.plusHours(1));
            schedulingDAO.update(schFound);
            Scheduling schUpdated = schedulingDAO.retrieve(sch.getId());
            System.out.println("Scheduling atualizado: start=" + schUpdated.getStartTime() + " | end=" + schUpdated.getEndTime());

            List<Scheduling> allSch = schedulingDAO.findAll();
            System.out.println("Total de schedulings: " + allSch.size());

            // Delete de teste (opcional): descomente se quiser ver funcionamento completo
            // schedulingDAO.delete(schUpdated);
            // serviceDAO.delete(sFound);
            // vehicleDAO.delete(vFound);
            // workshopDAO.delete(wFound);
            System.out.println("\n== Teste concluído com sucesso ==");
        } catch (Exception e) {
            System.err.println("Falha no teste: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Se o seu GenericDAO mantém um EntityManager aberto,
            // feche as instâncias aqui (se você tiver um método close() no DAO).
            // Exemplo:
            // workshopDAO.close();
            // vehicleDAO.close();
            // serviceDAO.close();
            // schedulingDAO.close();
        }
    }

    /**
     * Retorna o próximo dia útil (seg-sex).
     */
    private static LocalDate nextBusinessDay(LocalDate d) {
        LocalDate date = d.plusDays(1);
        switch (date.getDayOfWeek()) {
            case SATURDAY ->
                date = date.plusDays(2);
            case SUNDAY ->
                date = date.plusDays(1);
        }
        return date;
    }
}

