package io.github.codesapienbe;

import org.springframework.boot.SpringApplication;

public class TestReservatieApplication {

	public static void main(String[] args) {
		SpringApplication.from(ReservatieApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
