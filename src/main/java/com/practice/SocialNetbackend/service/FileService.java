package com.practice.SocialNetbackend.service;

import com.practice.SocialNetbackend.model.File;
import com.practice.SocialNetbackend.model.PathCatalog;
import com.practice.SocialNetbackend.model.Storage;
import com.practice.SocialNetbackend.model.Person;
import com.practice.SocialNetbackend.repositorie.StorageRepository;
import com.practice.SocialNetbackend.repositorie.FileRepository;
import com.practice.SocialNetbackend.repositorie.PathCatalogRepository;
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
    private final PathCatalogRepository pathCatalogRepository;

    public FileService(FileRepository fileRepository, StorageRepository storageRepository, PathCatalogRepository pathCatalogRepository) {
        this.fileRepository = fileRepository;
        this.storageRepository = storageRepository;
        this.pathCatalogRepository = pathCatalogRepository;
    }

    @Transactional
    public void save(MultipartFile file, Person person, String pathCatalog) throws CatalogNotFoundException,
            NotCreationException {
        File saveFile = new File();
        saveFile.setExtension(file.getContentType());
        saveFile.setName(file.getOriginalFilename());
        Storage storage = storageRepository.findByPerson(person)
                .orElseThrow(() -> new CatalogNotFoundException("Storage not found"));
        PathCatalog pathCatalogSave = new PathCatalog();
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
        try(InputStream is = file.getInputStream()){
            ByteArrayOutputStream baos = new ByteArrayOutputStream ();
            Thumbnails.of(is).size(160, 74).outputFormat("jpeg").toOutputStream(baos);
            saveFile.setPreview(baos.toByteArray());
            baos.close();
        }catch (IOException ignore){}
        fileRepository.save(saveFile);
    }

    public File getFile(Storage storage, String pathCatalog) throws FileNotFoundException, CatalogNotFoundException {
        String[] catalogNameFileName = separationPathAndFileName(pathCatalog);
        PathCatalog pathCatalogRoot = storage.getPathCatalogRoot();
        return fileRepository.findByNameAndPathCatalog(catalogNameFileName[1],
                        catalogNameFileName[0].equals("/") ? pathCatalogRoot : pathCatalogRoot.getPathCatalogs()
                .stream().filter((pathCatalog1 -> catalogNameFileName[0].equals(pathCatalog1.getPathName()))).findAny()
                .orElseThrow(() -> new CatalogNotFoundException("Catalog with name '" + catalogNameFileName[0] + "' not found")))
                .orElseThrow(() -> new FileNotFoundException("File with name '" + catalogNameFileName[1] + "' in catalog '"+ catalogNameFileName[0] +"' not found"));
    }

    private String[] separationPathAndFileName(String path){
        int lastSlashIndex = path.lastIndexOf("/");
        String[] catalogNameFileName = new String[2];
        catalogNameFileName[0] = path.substring(0, lastSlashIndex == 0 ? 1 : lastSlashIndex);
        catalogNameFileName[1] = path.substring(lastSlashIndex + 1);
        return catalogNameFileName;
    }

}
