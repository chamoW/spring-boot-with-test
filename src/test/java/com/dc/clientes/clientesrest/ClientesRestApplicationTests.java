package com.dc.clientes.clientesrest;

import com.dc.clientes.clientesrest.dao.IBancoRepository;
import com.dc.clientes.clientesrest.dao.ICuentaRepository;
import com.dc.clientes.clientesrest.exceptions.DineroInsuficienteException;
import com.dc.clientes.clientesrest.model.Banco;
import com.dc.clientes.clientesrest.model.Cuenta;
import com.dc.clientes.clientesrest.service.impl.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.dc.clientes.clientesrest.Datos.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
class ClientesRestApplicationTests {


    @MockBean
    ICuentaRepository cuentaRepository;

    @MockBean
    IBancoRepository bancoRepository;

    @Autowired
    CuentaService service;

    @BeforeEach
    void setUp() {
//		cuentaRepository = mock(CuentaRepository.class);
//		bancoRepository = mock(BancoRepository.class);
//		service = new CuentaServiceImpl(cuentaRepository, bancoRepository);
//		Datos.CUENTA_001.setSaldo(new BigDecimal("1000"));
//		Datos.CUENTA_002.setSaldo(new BigDecimal("2000"));
//		Datos.BANCO.setTotalTransferencias(0);
    }

    @Test
    void contextLoads() {
        when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());
        when(cuentaRepository.findById(2L)).thenReturn(crearCuenta002());
        when(bancoRepository.findById(1L)).thenReturn(crearBanco());

        BigDecimal saldoOrigen = service.revisarSaldo(1L);
        BigDecimal saldoDestino = service.revisarSaldo(2L);
        assertEquals("1000", saldoOrigen.toPlainString());
        assertEquals("2000", saldoDestino.toPlainString());

        service.transferir(1L, 2L, new BigDecimal("100"), 1L);

        saldoOrigen = service.revisarSaldo(1L);
        saldoDestino = service.revisarSaldo(2L);

        assertEquals("900", saldoOrigen.toPlainString());
        assertEquals("2100", saldoDestino.toPlainString());

        int total = service.revisarTotalTransferencias(1L);
        assertEquals(1, total);

        verify(cuentaRepository, times(3)).findById(1L);
        verify(cuentaRepository, times(3)).findById(2L);
        verify(cuentaRepository, times(2)).save(any(Cuenta.class));

        verify(bancoRepository, times(2)).findById(1L);
        verify(bancoRepository).save(any(Banco.class));

        verify(cuentaRepository, times(6)).findById(anyLong());
        verify(cuentaRepository, never()).findAll();
    }

    @Test
    void contextLoads2() {
        when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());
        when(cuentaRepository.findById(2L)).thenReturn(crearCuenta002());
        when(bancoRepository.findById(1L)).thenReturn(crearBanco());

        BigDecimal saldoOrigen = service.revisarSaldo(1L);
        BigDecimal saldoDestino = service.revisarSaldo(2L);
        assertEquals("1000", saldoOrigen.toPlainString());
        assertEquals("2000", saldoDestino.toPlainString());

        assertThrows(DineroInsuficienteException.class, ()-> {
            service.transferir(1L, 2L, new BigDecimal("1200"), 1L);
        });

        saldoOrigen = service.revisarSaldo(1L);
        saldoDestino = service.revisarSaldo(2L);

        assertEquals("1000", saldoOrigen.toPlainString());
        assertEquals("2000", saldoDestino.toPlainString());

        int total = service.revisarTotalTransferencias(1L);
        assertEquals(0, total);

        verify(cuentaRepository, times(3)).findById(1L);
        verify(cuentaRepository, times(2)).findById(2L);
        verify(cuentaRepository, never()).save(any(Cuenta.class));

        verify(bancoRepository, times(1)).findById(1L);
        verify(bancoRepository, never()).save(any(Banco.class));

        verify(cuentaRepository, times(5)).findById(anyLong());
        verify(cuentaRepository, never()).findAll();
    }

    @Test
    void contextLoads3() {
        when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());

        Cuenta cuenta1 = service.findById(1L);
        Cuenta cuenta2 = service.findById(1L);

        assertSame(cuenta1, cuenta2);
        assertTrue(cuenta1 == cuenta2);
        assertEquals("Andr??s", cuenta1.getPersona());
        assertEquals("Andr??s", cuenta2.getPersona());

        verify(cuentaRepository, times(2)).findById(1L);
    }

    @Test
    void testFindAll() {
        //given
        List<Cuenta> datos = Arrays.asList(crearCuenta001().get(), crearCuenta002().get());

        when(cuentaRepository.findAll()).thenReturn(datos);

        //when
        List<Cuenta> cuentas = service.findAll();

        //Then
        assertFalse(cuentas.isEmpty()       );
        assertEquals(2, cuentas.size());
       // assertTrue(cuentas.contains(crearCuenta002().get()));
    }

    @Test
    void testSave() {

        //Given
        Cuenta cuentaPepe = new Cuenta(null, "Pepe", new BigDecimal("3000"));

        when(cuentaRepository.save(any())).then(invocation -> {
            //Se captura lo enviado y se cambia la data
            Cuenta cuentaModificated = invocation.getArgument(0);
            cuentaModificated.setPersona("Wladimir L??pez");
            cuentaModificated.setId(3L);
            return cuentaModificated;
        });

        //When
        Cuenta cuenta = service.save(cuentaPepe);

        assertEquals(3l, cuenta.getId());
        assertEquals("Wladimir L??pez", cuenta.getPersona());
        assertEquals("3000", cuenta.getSaldo().toPlainString());

        verify(cuentaRepository.save(any()));
    }
}

