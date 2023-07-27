package com.ifpe.padroes.controller;

import com.ifpe.padroes.model.Sensor;
import com.ifpe.padroes.model.TipoSensor;
import com.ifpe.padroes.model.User;
import com.ifpe.padroes.repository.SensorRepository;
import com.ifpe.padroes.repository.UserRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/sensor")
public class SensorController {

    private final List<Sensor> sensorList = new ArrayList<>();
    private final SensorRepository sensorRepository;
    private final UserRepository userRepository;

    public SensorController(SensorRepository sensorRepository, UserRepository userRepository) {
        this.sensorRepository = sensorRepository;
        this.userRepository = userRepository;
    }

    // Endpoint GET para buscar todos os sensores
    @GetMapping("/get_all_sensor")
    public ResponseEntity<List<Sensor>> getAllSensors() {
        List<Sensor> sensors = sensorRepository.findAll();
        return ResponseEntity.ok(sensors);
    }

    // Endpoint POST para criar um novo sensor
    @PostMapping("/add_sensor")
    public ResponseEntity<String> createSensor(@RequestParam String nome, @RequestParam double valor, @RequestParam String estado, @RequestParam TipoSensor tipo, @RequestParam int autorId) {
        Optional<User> optionalUser = userRepository.findById(autorId);
        if (optionalUser.isPresent()) {
            User autor = optionalUser.get();
            Sensor newSensor = new Sensor(nome, valor, estado, tipo, autor);
            sensorRepository.save(newSensor); // Persistir no banco de dados
            return ResponseEntity.status(HttpStatus.CREATED).body("Sensor criado com sucesso:\n" + newSensor.toString());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint PUT para atualizar um sensor  pelo nome
    @PutMapping("/{nome}")
    public ResponseEntity<String> updateSensor(@PathVariable String nome, @RequestParam double valor, @RequestParam String estado, @RequestParam TipoSensor tipo, @RequestParam int autorId) {
        Optional<Sensor> optionalSensor = sensorRepository.findByNome(nome);
        if (optionalSensor.isPresent()) {
            Sensor sensor = optionalSensor.get();
            Optional<User> optionalUser = userRepository.findById(autorId);
            if (optionalUser.isPresent()) {
                User autor = optionalUser.get();
                sensor.setValor(valor);
                sensor.setEstado(estado);
                sensor.setTipo(tipo);
                sensor.setAutor(autor);
                sensorRepository.save(sensor); // Atualizar no banco de dados
                return ResponseEntity.ok("Sensor atualizado com sucesso:\n" + sensor.toString());
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint DELETE para deletar um sensor pelo nome
    @DeleteMapping("/{nome}")
    public ResponseEntity<String> deleteSensor(@PathVariable String nome) {
        Optional<Sensor> optionalSensor = sensorRepository.findByNome(nome);
        if (optionalSensor.isPresent()) {
            sensorRepository.delete(optionalSensor.get()); // Deletar no banco de dados
            return ResponseEntity.ok("Sensor deletado com sucesso");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint PUT para atualizar o estado de um sensor pelo nome
    @PutMapping("/{nome}/update_estado")
    public ResponseEntity<String> updateSensorEstadoByNome(@PathVariable String nome, @RequestParam String novoEstado) {
        Optional<Sensor> optionalSensor = sensorRepository.findByNome(nome);
        if (optionalSensor.isPresent()) {
            Sensor sensor = optionalSensor.get();
            sensor.setEstado(novoEstado);
            sensorRepository.save(sensor); // Atualizar o estado no banco de dados
            return ResponseEntity.ok("Estado do sensor atualizado com sucesso:\n" + sensor.toString());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}