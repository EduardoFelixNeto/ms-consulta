package br.com.ufpr.tads.dac.msconsulta.client;

import br.com.ufpr.tads.dac.msconsulta.dto.CompraPontosDTO;
import br.com.ufpr.tads.dac.msconsulta.dto.DebitoPontosDTO;
import br.com.ufpr.tads.dac.msconsulta.dto.SaldoDTO;
import br.com.ufpr.tads.dac.msconsulta.dto.TransacaoPontosDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "ms-usuarios", url = "${ms.usuarios.url}")
public interface UsuarioClient {

    @PostMapping("/usuarios/{id}/comprar-pontos")
    void comprarPontos(@PathVariable("id") Long id, @RequestBody CompraPontosDTO dto);

    @PostMapping("/usuarios/{id}/debitar-pontos")
    void debitarPontos(@PathVariable("id") Long id, @RequestBody DebitoPontosDTO dto);

    @GetMapping("/usuarios/{id}/saldo")
    SaldoDTO obterSaldo(@PathVariable("id") Long id);

    @GetMapping("/usuarios/{id}/extrato")
    List<TransacaoPontosDTO> obterExtrato(@PathVariable("id") Long id);
}

