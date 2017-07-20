package com.kgl.services;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import domain.Locador;

@Service
public class AdministratorService {
	private String filePath;

	public AdministratorService() {
		this.filePath = getClass().getClassLoader().getResource("administrators.json").getPath();
	}

	public AdministratorService(String filePath) {
		this.filePath = filePath;
	}

	public Locador getLocador() {
		try {
			return new Gson().fromJson(new FileReader(filePath), Locador.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();

			return new Locador();
		}
	}
}