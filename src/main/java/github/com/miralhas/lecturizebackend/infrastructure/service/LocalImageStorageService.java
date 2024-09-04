package github.com.miralhas.lecturizebackend.infrastructure.service;

import github.com.miralhas.lecturizebackend.domain.service.ImageStorageService;
import github.com.miralhas.lecturizebackend.infrastructure.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalImageStorageService implements ImageStorageService {

	@Value("${lecturizeit.storage.local.dir.images}")
	private Path storagePath;

	@Override
	public InputStream retrieve(String fileName) {
		try {
			return Files.newInputStream(getFilePath(fileName));
		} catch (IOException e) {
			throw new StorageException("Não foi possivel recuperar o arquivo de nome %s".formatted(fileName), e);
		}
	}

	@Override
	public void save(NewImage newImage) {
		Path filePath = getFilePath(newImage.getFileName());
		try {
			FileCopyUtils.copy(newImage.getInputStream(), Files.newOutputStream(filePath));
		} catch (IOException e) {
			throw new StorageException("Não foi possivel armazenar o arquivo de nome %s"
					.formatted(newImage.getFileName()), e);
		}
	}

	@Override
	public void remove(String fileName) {
		Path filePath = getFilePath(fileName);
		try {
			Files.deleteIfExists(filePath);
		} catch (IOException e) {
			throw new StorageException("Não foi possível excluir o arquivo", e);
		}
	}

	private Path getFilePath(String fileName) {
		return storagePath.resolve(fileName);
	}
}
