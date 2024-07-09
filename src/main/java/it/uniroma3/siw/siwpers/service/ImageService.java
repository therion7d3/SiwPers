package it.uniroma3.siw.siwpers.service;

import it.uniroma3.siw.siwpers.model.Evento;
import it.uniroma3.siw.siwpers.model.Image;
import it.uniroma3.siw.siwpers.repository.EventRepository;
import it.uniroma3.siw.siwpers.repository.UserRepository;
import it.uniroma3.siw.siwpers.model.User;
import it.uniroma3.siw.siwpers.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {


    private final EventRepository eventRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    public ImageService(UserRepository userRepository, ImageRepository imageRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.eventRepository = eventRepository;
    }

    public Optional<Image> getImageById(Long id) {
        return imageRepository.findById(id);
    }

    public Optional<Image> getImageByUserId(Long userId) {
        return imageRepository.findByUserId(userId);
    }

    public Optional<Image> getImageByEventId(Long eventId) {
        return imageRepository.findByEventoId(eventId);
    }

    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    public Image saveImageForUser(Long userId, MultipartFile file) throws IOException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            Image image = new Image();
            image.setData(file.getBytes());
            image.setUser(user);

            return imageRepository.save(image);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Image saveImageForEvent(MultipartFile file, Long eventId, int index) throws IOException {
        Evento event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));

        Image image;

        if (event.getImages().get(index) != null) {
            image = event.getImages().get(index);
            image.setData(file.getBytes());
            image.setEvento(event);
        } else {
            image = new Image();
            image.setData(file.getBytes());
            image.setEvento(event);
            event.getImages().set(index, image);
            imageRepository.save(image);
        }

        List<Image> images = event.getImages();
        for (int i = 0; i < images.size(); i++) {
            if (images.get(i) != null) {
                images.get(i).setImageOrder(i);
            }
        }

        eventRepository.save(event);
        return image;
    }

    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}

