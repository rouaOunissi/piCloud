package com.pi.projet.ServiceImp;

import org.springframework.stereotype.Service;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Document.Type;

@Service
public class TextAnalysisService {
    public String analyzeText(String text) {
    try (LanguageServiceClient language = LanguageServiceClient.create()) {
        Document doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();

        var response = language.analyzeEntities(doc);
        return response.getEntitiesList().toString();
    } catch (Exception e) {
        // Gérer les exceptions
        return "Error processing text analysis";
    }
}

}
