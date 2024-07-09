package it.uniroma3.siw.siwpers.model;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.*;

@EnableAutoConfiguration
@Entity
@Table(name = "eventi")
public class Evento {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 500)
    private String description;

    @Column(name = "indirizzo")
    private String indirizzo;

    @Column(name = "max_biglietti")
    private int maxTickets;

    @Column(name = "tipo_evento")
    private String tipoEvento;

    @Column(name = "data_evento")
    private String dataEvento;

    @ManyToOne
    @JoinColumn(name = "id_organizzatore")
    private User owner;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    @OrderColumn
    private List<Image> images;

    public Evento() {
        this.images = new ArrayList<>();
        Image image1 = new Image();
        Image image2 = new Image();
        Image image3 = new Image();
        image1.setEvento(this);
        image2.setEvento(this);
        image3.setEvento(this);
        this.images.add(image1);
        this.images.add(image2);
        this.images.add(image3);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Image> getImages() {
        return images;
    }

    public void addImage(Image newImage) {
        this.images.add(newImage);
    }


    public void replaceImage(int index, Image image) {
        this.images.set(index, image);
    }

    public void removeImage(Image image) {
        this.images.remove(image);
        image.setEvento(null);
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public int getMaxTickets() {
        return maxTickets;
    }

    public void setMaxTickets(int maxTickets) {
        this.maxTickets = maxTickets;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDataEvento(String dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getDataEvento() {
        return dataEvento;
    }

    public String getIndirizzo() {
        return indirizzo;
    }
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
