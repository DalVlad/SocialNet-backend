package com.practice.SocialNetbackend.service;

import com.practice.SocialNetbackend.dto.FileLikeDTO;
import com.practice.SocialNetbackend.model.File;
import com.practice.SocialNetbackend.model.PathCatalog;
import com.practice.SocialNetbackend.model.Storage;
import com.practice.SocialNetbackend.model.Person;
import com.practice.SocialNetbackend.repositorie.StorageRepository;
import com.practice.SocialNetbackend.repositorie.FileRepository;
import com.practice.SocialNetbackend.util.CatalogNotFoundException;
import com.practice.SocialNetbackend.util.FileNotFoundException;
import com.practice.SocialNetbackend.util.NotCreationException;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Transactional(readOnly = true)
public class FileService {

    private final FileRepository fileRepository;
    private final StorageRepository storageRepository;

    public FileService(FileRepository fileRepository, StorageRepository storageRepository) {
        this.fileRepository = fileRepository;
        this.storageRepository = storageRepository;
    }

    @Transactional
    public void save(MultipartFile file, Person person, String pathCatalog, MultipartFile preview) throws CatalogNotFoundException,
            NotCreationException {
        File saveFile = new File();
        saveFile.setExtension(file.getContentType());
        saveFile.setName(file.getOriginalFilename());
        Storage storage = storageRepository.findByPerson(person)
                .orElseThrow(() -> new CatalogNotFoundException("Storage not found"));
        PathCatalog pathCatalogSave;
        if(pathCatalog.equals("/")){
            pathCatalogSave = storage.getPathCatalogRoot();
        }else {
            pathCatalogSave = storage.getPathCatalogRoot().getPathCatalogs()
                    .stream().filter((pathCatalog1 -> pathCatalog.equals(pathCatalog1.getPathName())))
                    .findAny().orElseThrow(() -> new CatalogNotFoundException(String.format("Catalog with path '%s' not found", pathCatalog)));
        }
        saveFile.setPathCatalog(pathCatalogSave);
        try(InputStream is = file.getInputStream()){
            saveFile.setFile(is.readAllBytes());
        }catch (IOException e){
            throw new NotCreationException(e.getMessage());
        }
        try(InputStream is = preview.getInputStream()){
            ByteArrayOutputStream baos = new ByteArrayOutputStream ();
            Thumbnails.of(is).size(160, 74).outputFormat("jpeg").toOutputStream(baos);
            saveFile.setPreview(baos.toByteArray());
            baos.close();
        }catch (IOException ignore){}
        saveFile = fileRepository.save(saveFile);
        saveFile.setName(saveFile.getName() + saveFile.getId());
        fileRepository.save(saveFile);
    }

    public File getFile(Storage storage, String pathCatalogAndNameFile) throws FileNotFoundException {
        String[] catalogNameFileId = separationPathAndFileId(pathCatalogAndNameFile);
        return fileRepository.findById(Long.parseLong(catalogNameFileId[1]))
                .orElseThrow(() -> new FileNotFoundException("File with id '" + catalogNameFileId[1] + "' not found"));
    }

    public FileLikeDTO getFileLike(Person person, File file) throws FileNotFoundException {
        return fileRepository.getFileLikes(person, file).orElseThrow(() -> new FileNotFoundException("Like for file with id '" + file.getId() + "' not found"));
    }

    @Transactional
    public void setFileLike(Person person, File file) {
        if(file.getLikes().contains(person)){
            file.getLikes().remove(person);
        }else {
            file.getLikes().add(person);
        }
        fileRepository.save(file);
    }

    @Transactional
    public void deleteFile(Storage storage, String pathCatalog) throws FileNotFoundException, CatalogNotFoundException {
        String[] catalogNameFileId = separationPathAndFileId(pathCatalog);
        PathCatalog pathCatalogRoot = storage.getPathCatalogRoot();
        fileRepository.deleteByIdAndPathCatalog(Long.parseLong(catalogNameFileId[1]),
                catalogNameFileId[0].equals("/") ? pathCatalogRoot : pathCatalogRoot.getPathCatalogs()
                        .stream().filter((pathCatalog1 -> catalogNameFileId[0].equals(pathCatalog1.getPathName()))).findAny()
                        .orElseThrow(() -> new CatalogNotFoundException("Catalog with name '" + catalogNameFileId[0] + "' not found")));
    }

    private String[] separationPathAndFileId(String path){
        int lastSlashIndex = path.lastIndexOf("/");
        String[] catalogNameFileName = new String[2];
        catalogNameFileName[0] = path.substring(0, lastSlashIndex == 0 ? 1 : lastSlashIndex);
        catalogNameFileName[1] = path.substring(lastSlashIndex + 1);
        return catalogNameFileName;
    }

}
