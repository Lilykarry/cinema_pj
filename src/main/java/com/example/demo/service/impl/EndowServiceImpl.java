package com.example.demo.service.impl;

import com.example.demo.domain.UpsertEndow;
import com.example.demo.exception.MovieNotFoundExeption;
import com.example.demo.model.Endow;
import com.example.demo.repository.EndowRepository;
import com.example.demo.service.EndowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

@Service
public class EndowServiceImpl implements EndowService {

    @Autowired
    private EndowRepository endowRepository;


    @Override
    public List<Endow> showAllPromotions() {
        return endowRepository.findAll();
    }

    @Override
    public List<Endow> searchEndowByTitle(String keyword) {
        return endowRepository.findByEndowTitle(keyword);
    }

    @Override
    public void deleteEndowById(String endowId) {
        endowRepository.deleteById(endowId);
    }
    public Endow get(String endowId) throws MovieNotFoundExeption {
        Optional<Endow> result=endowRepository.findById(endowId);
        if(result.isPresent()){
            return result.get();
        }

        throw new MovieNotFoundExeption("Could not find any movie with ID"+endowId);
    }
    @Override
    public void save(UpsertEndow endow) throws IOException {
        Endow entity = endowRepository.findById(endow.getEndowId()).orElse(new Endow());
        if (endow.getEndowImage() != null && !endow.getEndowImage().isEmpty()) {
            entity.setEndowImage(generateImagePath(endow.getEndowImage()));
        }
        entity.setEndowId(endow.getEndowId());
        entity.setEndowTitle(endow.getEndowTitle());
        entity.setEndowContent(endow.getEndowContent());

        endowRepository.save(entity);
    }
    private String generateImagePath(MultipartFile file) throws IOException {
        String fileExtension = getFileExtension(file.getOriginalFilename());

        File file1 = new File("/Users/LENOVO/IdeaProjects/cinama_PJ_IMG/img/" + file.getOriginalFilename());

        try (OutputStream os = new FileOutputStream(file1)) {
            os.write(file.getBytes());
        }

        return file.getOriginalFilename();
    }

    private String getFileExtension(String originalFilename) {
        return "";
    }
}
