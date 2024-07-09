    package it.uniroma3.siw.siwpers.controller;

    import it.uniroma3.siw.siwpers.model.Image;
    import it.uniroma3.siw.siwpers.response.ImageResponse;
    import it.uniroma3.siw.siwpers.service.ImageService;
    import org.springframework.http.HttpHeaders;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.IOException;
    import java.util.Optional;

    @RestController
    @RequestMapping("/images")
    public class ImageController {

        private final ImageService imageService;

        public ImageController(ImageService imageService) {
            this.imageService = imageService;
        }

        @GetMapping("/{id}")
        public ResponseEntity<byte[]> getImageById(@PathVariable("id") Long id) {
            Optional<Image> image = imageService.getImageById(id);
            if (image.isPresent()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + id + ".jpg\"")
                        .body(image.get().getData());
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @PostMapping
        public ResponseEntity<Image> createImage(@RequestParam("file") MultipartFile file) {
            try {
                Image image = new Image();
                image.setData(file.getBytes());
                Image savedImage = imageService.saveImage(image);
                return ResponseEntity.ok(savedImage);
            } catch (IOException e) {
                return ResponseEntity.badRequest().build();
            }
        }

        @GetMapping("/user/{userId}")
        public ResponseEntity<byte[]> getImageByUserId(@PathVariable("userId") Long userId) {
            Optional<Image> image = imageService.getImageByUserId(userId);
            if (image.isPresent()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("image/jpeg"))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"user_" + userId + ".jpg\"")
                        .body(image.get().getData());
            } else {
                return ResponseEntity.notFound().build();
            }
        }



        @PostMapping("/upload/user/{userId}")
        public ResponseEntity<ImageResponse> uploadImageForUser(@PathVariable("userId") Long userId, @RequestParam("file") MultipartFile file) {
            try {
                Optional<Image> pfp = imageService.getImageByUserId(userId);
                if (pfp.isPresent()) {
                    Image profileImage = pfp.get();
                    profileImage.setData(file.getBytes());
                    imageService.saveImage(profileImage);

                    ImageResponse response = new ImageResponse(profileImage.getId(), profileImage.getData(),
                            profileImage.getUser().getId(),
                            profileImage.getEvento() != null ? profileImage.getEvento().getId() : null);
                    return ResponseEntity.ok(response);
                } else {
                    Image savedImage = imageService.saveImageForUser(userId, file);

                    ImageResponse response = new ImageResponse(savedImage.getId(), savedImage.getData(),
                            savedImage.getUser().getId(),
                            savedImage.getEvento() != null ? savedImage.getEvento().getId() : null);
                    return ResponseEntity.ok(response);
                }
            } catch (IOException e) {
                return ResponseEntity.badRequest().build();
            }
        }

        @PostMapping("/upload/event/{eventId}")
        public ResponseEntity<ImageResponse> uploadImageForEvent(@PathVariable("eventId") Long eventId, @RequestParam("file") MultipartFile file,@RequestParam("index") int index) {
            try {
                Image savedImage = imageService.saveImageForEvent(file, eventId, index);
                ImageResponse response = new ImageResponse(savedImage.getId(), savedImage.getData(), savedImage.getEvento().getId(), savedImage.getUser()  != null ? savedImage.getUser().getId() : null);
                return ResponseEntity.ok(response);
            } catch (IOException e) {
                return ResponseEntity.badRequest().build();
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteImage(@PathVariable("id") Long id) {
            imageService.deleteImage(id);
            return ResponseEntity.noContent().build();
        }
    }
