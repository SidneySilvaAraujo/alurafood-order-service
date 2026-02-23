package br.com.alurafood.pedidos.controller;

import br.com.alurafood.pedidos.dto.PedidoDto;
import br.com.alurafood.pedidos.dto.StatusDto;
import br.com.alurafood.pedidos.service.PedidoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService service;

    @GetMapping
    public List<PedidoDto> listarTodos() {
        return service.obtertodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity listarPorId(@PathVariable @NotNull Long id) {
        PedidoDto dto = service.obterPorId(id);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PedidoDto> realizaPedido(@RequestBody @Valid PedidoDto dto, UriComponentsBuilder uribuilder) {
        PedidoDto pedidoRealizado = service.criarPedido(dto);

        Uri endereco = uribuilder.path("/pedidos/{id}").buildAndExpand(pedidoRealizado.getId()).toUri();

        return ResponseEntity.created(endereco).body(pedidoRealizado);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PedidoDto> atualizaStatus(@PathVariable @NotNull Long id, @RequestBody StatusDto status) {
        PedidoDto dto = service.atualizaStatus(id, status);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}/pago")
    public ResponseEntity<Void> altorizaPagamento(@PathVariable @NotNull Long id) {
        service.aprovaPagamentoPedido(id);

        return ResponseEntity.ok().build();
    }
}

