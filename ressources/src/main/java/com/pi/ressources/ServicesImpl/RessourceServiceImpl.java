package com.pi.ressources.ServicesImpl;

import com.pi.ressources.Dao.RessourceDao;
import com.pi.ressources.Enum.TypeRessource;
import com.pi.ressources.Services.FileUploadService;
import com.pi.ressources.Services.RessourceService;
import com.pi.ressources.entities.Ressource;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Date;

import static com.google.common.io.Files.getFileExtension;

@Service
public  class RessourceServiceImpl implements RessourceService {

     @Resource
     private RessourceDao ressourceDao;




        @Override
           public Ressource addRessource(Ressource ressource) {
               ressource.setDateCreation(new Date());
               ressource.setIdUser(Long.valueOf(1));
               ressource.setNbrReact(Long.valueOf(0));
               return this.ressourceDao.save(ressource);
          }

          @Resource
           FileUploadService fileUploadService;

        @Override
        public Optional<Ressource> addRessourceFile(Ressource ressource, MultipartFile file) {
            try {
                String UrlFile = fileUploadService.uploadFile(file);
                if (UrlFile !=null)
                {   String fileName = file.getOriginalFilename();
                    String fileType = getFileExtension(fileName);

                    ressource.setFileName(fileName);
                    ressource.setFileType(fileType);

                    ressource.setDateCreation(new Date());
                    ressource.setIdUser(Long.valueOf(1));
                    ressource.setNbrReact(Long.valueOf(0));
                    ressource.setUrlFile(UrlFile);
                    return Optional.of(this.ressourceDao.save(ressource));

                }
                return Optional.empty();
            }catch (Exception ee)
            {
                ee.printStackTrace();
                return Optional.empty();
            }

    }


    @Override
     public List<Ressource> findAll()
    {
        return ressourceDao.findAll();
    }

   /**
    * find ressource by idRessource*/
    @Override
    public Ressource findById(Long id){
        final Optional<Ressource > optionalRessource=ressourceDao.findById(id);
        if (optionalRessource.isPresent())
        {
            return optionalRessource.get();
        }
        return null;
    }

    /**
     * find liste des ressources by idUser (pour chaque user)*/

    @Override
    public List<Ressource> getByIdUser(Long userid){
        return ressourceDao.findByUserId(userid);
    }

    @Override
    public Ressource getByUrlFile(String urlFile){
        return ressourceDao.findByUrlFile(urlFile);
    }



    @Override
    public List<Ressource> getRessourcesByType(TypeRessource typeResoource)
    {
        return ressourceDao.findByTypeRessource(typeResoource);
    }


   @Override
    public Ressource updateRessource(Long idRessource, Ressource ressource)
    {
        final Optional<Ressource > optionalRessource = ressourceDao.findById(idRessource);
        if (optionalRessource.isPresent())
        {
            Ressource existingRessource = optionalRessource.get();

            existingRessource.setTitre(ressource.getTitre());
            existingRessource.setDescription(ressource.getDescription());
            existingRessource.setTypeR(ressource.getTypeR());
            existingRessource.setUrlFile(ressource.getUrlFile());

            return  ressourceDao.save(existingRessource);
        }
        else {return null;}
    }



   /** @Override
    public String deteleRessource(Long id)
    {
        ressourceDao.deleteById(id);
        return "ressource removed !!";
    }
    **/


   //à revérifier
    @Override
      public String deteleRessource(Long id, Long idUser)
    {
        Optional<Ressource> optionalRessource = ressourceDao.findById(id);
        if (optionalRessource.isPresent()) {
            Ressource ressource = optionalRessource.get();
            if (ressource.getIdUser().equals(idUser)) {
                ressourceDao.delete(ressource);
                return "Ressource removed !!";
            } else {
                return "Ressource does not belong to the user!";
            }
        } else {
            return "Ressource not found!";
        }
    }






}