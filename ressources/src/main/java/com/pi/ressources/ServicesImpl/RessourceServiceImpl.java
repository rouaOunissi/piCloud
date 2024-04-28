package com.pi.ressources.ServicesImpl;

import com.pi.ressources.Dao.ReactionDao;
import com.pi.ressources.Dao.RessourceDao;
import com.pi.ressources.Enum.TypeRessource;
import com.pi.ressources.Services.FileUploadService;
import com.pi.ressources.Services.RessourceService;
import com.pi.ressources.Services.SynonymService;
import com.pi.ressources.entities.Reaction;
import com.pi.ressources.entities.Ressource;
import jakarta.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import java.util.stream.Collectors;
import static com.google.common.io.Files.getFileExtension;


@Service
public  class RessourceServiceImpl implements RessourceService {

     @Resource
     private RessourceDao ressourceDao;

     @Autowired
     private SynonymService synonymService;

    @Resource
    private ReactionDao reactionDao;



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
     * Liste des types de ressource dans l'énumération */
    @Override
    public List<String> getAllTypeRessourceNames() {
        return Arrays.stream(TypeRessource.values())
                .map(Enum::name)
                .collect(Collectors.toList());
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
    public Optional<Ressource> updateRessource(Long idRessource, Ressource ressource, MultipartFile file) {
        final Optional<Ressource> optionalRessource = ressourceDao.findById(idRessource);
        try {
            if (file != null && !file.isEmpty()) {
                String UrlFile = fileUploadService.uploadFile(file);
                if (UrlFile != null) {
                    String fileName = file.getOriginalFilename();
                    String fileType = getFileExtension(fileName);

                    Ressource existingRessource = optionalRessource.orElseThrow(() -> new IllegalArgumentException("Resouce not found"));

                    existingRessource.setTitre(ressource.getTitre());
                    existingRessource.setDescription(ressource.getDescription());
                    existingRessource.setFileName(fileName);
                    existingRessource.setFileType(fileType);
                    existingRessource.setTypeR(ressource.getTypeR());
                    existingRessource.setUrlFile(UrlFile);

                    return Optional.of(this.ressourceDao.save(existingRessource));
                }
            } else {

                Ressource existingRessource = optionalRessource.orElseThrow(() -> new IllegalArgumentException("Resouce not found"));

                existingRessource.setTitre(ressource.getTitre());
                existingRessource.setDescription(ressource.getDescription());
                existingRessource.setTypeR(ressource.getTypeR());
                return Optional.of(this.ressourceDao.save(existingRessource));
            }
            return Optional.empty();
        } catch (Exception ee) {
            ee.printStackTrace();
            return Optional.empty();
        }
    }


   @Override
    public ResponseEntity<?> deteleRessource(Long id)
    {Optional <Ressource> ressource = ressourceDao.findById(id);
        if(ressource.isPresent()){
            ressourceDao.deleteById(id);
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        }
        else
            return ResponseEntity.badRequest().body("Ressource do not exist");
    }


    @Override
    public String GenerateInvoicePDF(Long id) {
        try {

            Optional<Ressource> optionalRessource = ressourceDao.findById(id);
            if (optionalRessource.isPresent()) {

                String pdfUrl = optionalRessource.get().getUrlFile();

                return pdfUrl;
            } else {

                return "Resource not found for the given ID";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while generating PDF invoice";
        }
    }

    @Override
    public List<Ressource> findByTitreContaining(String titre) {
        return ressourceDao.findByTitreContainingIgnoreCase(titre);
    }


    @Override
    public boolean hasUserReactedToResource(long resourceId ,long userId) {
        // Vérifie si l'utilisateur a réagi à la ressource spécifiée dans la base de données
        Optional<Reaction> userReaction = reactionDao.findReactionByIdReactionAndIdUser2(resourceId,userId);
        return userReaction.isPresent();
    }

    @Override
    public ResponseEntity<?> reactToRessource(Long idRess) {
        // Récupérer l'ID de l'utilisateur à partir de l'objet Principal
        // Long userId = getUserIdFromPrincipal(principal);
        Long userId = 1L;
        Optional<Ressource> optionalRessource = ressourceDao.findById(idRess);

        if (!optionalRessource.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Ressource ressource = optionalRessource.get();
        // Vérifier si l'utilisateur a déjà réagi à cette ressource
        Reaction existingReaction = reactionDao.findReactionByIdReactionAndIdUser(idRess,userId);

        if (existingReaction != null) {
            // Si l'utilisateur a déjà réagi, supprimer sa réaction
            reactionDao.delete(existingReaction);

            // Décrémenter le nombre de réactions uniquement si la réaction existait déjà
            Long nbrReact = ressource.getNbrReact();
            nbrReact--;
            ressource.setNbrReact(nbrReact);
        } else {
            // Si l'utilisateur n'a pas réagi, enregistrer sa réaction
            Reaction reaction = new Reaction();
            reaction.setIdUser(userId);
            reaction.setRessource(ressource);
            reactionDao.save(reaction);

            // Incrémenter le nombre de réactions uniquement si c'est une nouvelle réaction
            Long nbrReact = ressource.getNbrReact();
            nbrReact++;
            ressource.setNbrReact(nbrReact);
        }

        ressourceDao.save(ressource);

        return ResponseEntity.ok().build();
    }

    @Override
    public List<Ressource> getRessourcesOrderedByNbrReactDesc() {
        return ressourceDao.findAllOrderByNbrReactDesc();
    }


    @Override
    public List<Ressource> searchRessourcesBySynonyms(String word) {
        String[] synonyms = synonymService.getSynonyms(word);
        System.out.println("Synonyms: " + Arrays.toString(synonyms));
        List<Ressource> results = new ArrayList<>();
        for (String synonym : synonyms) {
            // Rechercher les ressources par titre ou description contenant le synonyme
            List<Ressource> synonymResults = ressourceDao.findByTitleContainingOrDescriptionContaining(synonym);
            results.addAll(synonymResults);
        }
        return results;
    }


    @Override
    public List<Ressource> searchRessourcesByKeyword(String word) {
        String[] synonymsArray = synonymService.getSynonyms(word);
        if (synonymsArray == null || synonymsArray.length == 0) {
            return Collections.emptyList();
        }

        List<String> synonyms = Arrays.asList(synonymsArray);

        Set<Ressource> results = new HashSet<>();
        for (String synonym : synonyms) {
            List<Ressource> matchingRessources = ressourceDao.findByTitleContainingOrDescriptionContaining(synonym);
            results.addAll(matchingRessources);
        }
        return new ArrayList<>(results);
    }

}
