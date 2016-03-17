/*
 * Copyright (C) 2016 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.persistence.DaoFactory;
import edu.eci.pdsw.samples.persistence.DaoPaciente;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class PacientePersistenceTest {
    
    public PacientePersistenceTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void databaseConnectionTest() throws IOException, PersistenceException{
        InputStream input = null;
        input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties");
        Properties properties=new Properties();
        properties.load(input);
        
        DaoFactory daof=DaoFactory.getInstance(properties);
        
        daof.beginSession();
        
        //Prueba 1
        DaoPaciente dp = daof.getDaoPaciente();
        Paciente  p = new Paciente(987, "CC", "Juan Sanchez", java.sql.Date.valueOf("1995-01-01"));
        Set<Consulta> consultas = new LinkedHashSet<>();
        Consulta c = new Consulta(java.sql.Date.valueOf("2000-10-01"), "Consulta general");
        Consulta c1 = new Consulta(java.sql.Date.valueOf("2001-01-01"), "Consulta de control");
        consultas.add(c);
        consultas.add(c1);
        p.setConsultas(consultas);
        dp.save(p);
        Paciente res = dp.load(987, "CC");
        assertEquals(p,res);
        assertEquals(2, res.getConsultas().size());
        
        //Prueba 2  
        Paciente  p1 = new Paciente(9876, "TI", "David Lopez", java.sql.Date.valueOf("2005-01-01"));
        dp.save(p1);
        Paciente res1 = dp.load(9876, "TI");
        assertEquals(res1, p1);
        assertEquals(0, res1.getConsultas().size());
      
        //Prueba 3
        Paciente p2 = new Paciente(999, "CC", "David Chisco", java.sql.Date.valueOf("2006-05-06"));
        Set<Consulta> consultas2 = new LinkedHashSet<>();
        Consulta c2 = new Consulta(java.sql.Date.valueOf("2002-05-04"), "Consulta por urgencias");
        consultas2.add(c2);
        p2.setConsultas(consultas2);
        dp.save(p2);
        Paciente respu = dp.load(999, "CC");
        assertEquals(1, respu.getConsultas().size());
        
        //Prueba 4
        Paciente res3 = dp.load(987, "CC");
        if (res3.getConsultas().size() <= 1) {
            fail();
        }
        
        //IMPLEMENTACION DE LAS PRUEBAS
        //fail("Pruebas no implementadas");


        daof.commitTransaction();
        daof.endSession();        
    }
    
//    @Test
//    public void databaseConnectionTest2() throws IOException, PersistenceException{
//        InputStream input = null;
//        input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties");
//        Properties properties=new Properties();
//        properties.load(input);
//        
//        DaoFactory daof2=DaoFactory.getInstance(properties);
//        
//        daof2.beginSession();
//          //Prueba 2  
//          DaoPaciente dp = daof2.getDaoPaciente();
//        Paciente  p1 = new Paciente(9876, "TI", "David Lopez", java.sql.Date.valueOf("2005-01-01"));
//        dp.save(p1);
//        Paciente res1 = dp.load(9876, "TI");
//        assertEquals(res1, p1);
//        assertEquals(0, res1.getConsultas().size());
//        daof2.commitTransaction();
//        daof2.endSession();  
//    }
    
//    @Test
//    public void databaseConnectionTest3() throws IOException, PersistenceException{
//        InputStream input = null;
//        input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties");
//        Properties properties=new Properties();
//        properties.load(input);
//        
//        DaoFactory daof3=DaoFactory.getInstance(properties);
//        
//     
//       daof3.beginSession();
//        //Prueba 3
//        DaoPaciente daop = daof3.getDaoPaciente();
//        Paciente p2 = new Paciente(999, "CC", "David Chisco", java.sql.Date.valueOf("2006-05-06"));
//        Set<Consulta> consultas2 = new LinkedHashSet<>();
//        Consulta c2 = new Consulta(java.sql.Date.valueOf("2002-05-04"), "Consulta por urgencias");
//        consultas2.add(c2);
//        p2.setConsultas(consultas2);
//        daop.save(p2);
//        Paciente respu = daop.load(999, "CC");
//        assertEquals(1, respu.getConsultas().size());
//        
//        daof3.commitTransaction();
//        daof3.endSession();  
//    }
//    @Test
//    public void databaseConnectionTest4() throws IOException, PersistenceException{
//        InputStream input = null;
//        input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties");
//        Properties properties=new Properties();
//        properties.load(input);
//        
//        DaoFactory daof=DaoFactory.getInstance(properties);
//        
//        daof.beginSession();
//          //Prueba 4
//          DaoPaciente dp = daof.getDaoPaciente();
//        Paciente res3 = dp.load(987, "CC");
//        if (res3.getConsultas().size() <= 1){
//            fail();
//        }
//        daof.commitTransaction();
//        daof.endSession();  
//    }
    
}
