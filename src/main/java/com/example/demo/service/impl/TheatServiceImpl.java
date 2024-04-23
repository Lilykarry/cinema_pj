package com.example.demo.service.impl;

import com.example.demo.domain.UpsertThreat;
import com.example.demo.exception.MovieNotFoundExeption;
import com.example.demo.model.Movie;
import com.example.demo.model.Threat;
import com.example.demo.repository.ThreatRepository;
import com.example.demo.service.ThreatService;
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
public class TheatServiceImpl implements ThreatService {

    @Autowired
    private ThreatRepository theatRepository;

    @Override
    public List<Threat> showAllTheater() {
        return theatRepository.findAll();
    }

    @Override
    public void save(UpsertThreat threat) throws IOException, MovieNotFoundExeption {
        Threat entity;

        // Nếu id được gửi qua là null hoặc không tồn tại trong cơ sở dữ liệu,
        // ta tạo một đối tượng Threat mới.
        if (threat.getId() == null || !theatRepository.existsById(threat.getId())) {
            // Lấy ID lớn nhất hiện có trong cơ sở dữ liệu
            Integer maxId = theatRepository.findMaxId();

            // Tăng giá trị ID lớn nhất thêm một đơn vị để tạo ID mới cho Threat
            Integer newId = (maxId != null) ? maxId + 1 : 1;

            entity = new Threat();
            entity.setId(newId);
        } else {
            // Ngược lại, nếu id đã tồn tại, ta lấy đối tượng từ cơ sở dữ liệu.
            Optional<Threat> existingThreat = theatRepository.findById(threat.getId());
            if (!existingThreat.isPresent()) {
                throw new MovieNotFoundExeption("Threat with ID " + threat.getId() + " not found.");
            }
            entity = existingThreat.get();
        }

        // Đặt thông tin khác của threat
        entity.setName(threat.getName());
        entity.setPhone(threat.getPhone());
        entity.setAddress(threat.getAddress());
        entity.setRegion(threat.getRegion());

        // Đặt ảnh cho threat nếu có
        if (threat.getImg() != null && !threat.getImg().isEmpty()) {
            entity.setImg(generateImagePath(threat.getImg()));
        }

        // Lưu hoặc cập nhật đối tượng vào cơ sở dữ liệu
        theatRepository.save(entity);
    }


    private String generateImagePath(MultipartFile file) throws IOException {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        // Tạo tên file mới với đường dẫn thích hợp
        String newFileName = "img_" + System.currentTimeMillis() + fileExtension;
        File file2 = new File("/Users/LENOVO/IdeaProjects/cinama_PJ_IMG/img/" + newFileName);
        try (OutputStream os = new FileOutputStream(file2)) {
            os.write(file.getBytes());
        }
        return newFileName;
    }

    private String getFileExtension(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }
    @Override
    public Threat get(Integer Id) throws MovieNotFoundExeption {
        Optional<Threat> result=theatRepository.findById(Id);
        if(result.isPresent()){
            return result.get();
        }
        throw new MovieNotFoundExeption("Could not find any threat with ID"+Id);
    }

    @Override
    public void deleteThreatById(Integer id) {
         theatRepository.deleteById(id);
    }

}
