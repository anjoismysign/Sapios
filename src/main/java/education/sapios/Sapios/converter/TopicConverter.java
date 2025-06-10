package education.sapios.Sapios.converter;

import education.sapios.Sapios.entity.hallway.Topic;
import education.sapios.Sapios.repository.TopicRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class TopicConverter implements Converter<Topic> {

    @Inject
    private TopicRepository topicRepository;
    
    @Override
    public Topic getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        
        try {
            Long id = Long.valueOf(value);
            return topicRepository.findById(id).orElse(null);
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Topic topic) {
        if (topic == null) {
            return "";
        }
        
        return String.valueOf(topic.getId());
    }
}
