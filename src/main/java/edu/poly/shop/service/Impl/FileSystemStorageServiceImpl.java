package edu.poly.shop.service.Impl;

import edu.poly.shop.config.StorageProperties;
import edu.poly.shop.exception.StorageException;
import edu.poly.shop.exception.StorageFileNotFoundException;
import edu.poly.shop.service.StorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageServiceImpl implements StorageService {
    private final Path rootLocation;

    @Override
    public String getStorageFilename(MultipartFile file, String id){   //file: là tên file gốc, ví dụ:anh-dep.png, output của function này sẽ đổi tên ảnh, để không bị trùng lặp
        String ext = FilenameUtils.getExtension(file.getOriginalFilename()); //get out extension of images( like png, jpg, jpeg..)
        return "p" + id + "." + ext;
    }

    public FileSystemStorageServiceImpl(StorageProperties properties){
        this.rootLocation = Paths.get(properties.getLocation());            //lấy thư mục dùng để lưu file. lấy ra từ appliction.properties.
    }

    @Override
    public void store(MultipartFile file, String storedFilename) { //lưu file, với tham số đầu là file lấy từ view, tham số thứ 2 là tên file(tên file đã được đặt random)
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file");
            }
            Path destinationFile = this.rootLocation.resolve(Paths.get(storedFilename))     //resolve: dùng để path dựa trên rootLocation. exg: root: c/nmcong.. -> resolve: c/nmcong/fileName
                    .normalize().toAbsolutePath();
            if(!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())){     //So sánh path lấy ở trên và path lấy ở application.properties. Nếu file lưu ở ngoài stored_location(ví dụ: c:/nmcong/images) thì sẽ hiển thị message là không đc lưu ở ngoài folder
                throw new StorageException("Cannot store file outside current directory");
            }
            try(InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);     //copy file và path lấy được ở trên, và replace image if exist
            }
        } catch (Exception e) {
            throw new StorageException("Failed to store file", e);    //nếu mà không lưu được thì báo lỗi, có truyền thông tin của exception.
        }
    }

    @Override
    public Resource loadAsResource(String filename){  //for display images
        try {
            Path file = load(filename);
            org.springframework.core.io.Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }
            throw new StorageFileNotFoundException("Could not read file: " + filename);
        }catch (Exception e) {
            throw new StorageFileNotFoundException("Could not read file " +filename);
        }
    }
    @Override
    public Path load(String filename){
        return rootLocation.resolve(filename);
    }

    @Override
    public void delete(String storedFilename) throws IOException{
        Path destinationFile = rootLocation.resolve(Paths.get(storedFilename)).normalize().toAbsolutePath();

        Files.delete(destinationFile);
    }
    @Override
    public void init(){  //Hàm init này được init bằng @Bean ở function main.
        try{
            Files.createDirectories(rootLocation);
            System.out.println(rootLocation.toString());
        }catch (Exception e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
