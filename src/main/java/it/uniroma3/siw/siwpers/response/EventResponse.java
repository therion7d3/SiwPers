package it.uniroma3.siw.siwpers.response;

import it.uniroma3.siw.siwpers.model.Evento;

public class EventResponse {
    private final Long id;
    private final String title;
    private final String description;
    private final Long ownerId;
    private final String ownerName;
    private final int maxTickets;
    private final String tipoEvento;
    private final byte[] immagine1;
    private final byte[] immagine2;
    private final byte[] immagine3;
    private final String dataEvento;
    private final String indirizzo;

    public EventResponse(Evento event) {
        this.id = event.getId();
        this.title = event.getTitle();
        this.description = event.getDescription();
        this.ownerId = event.getOwner().getId();
        this.ownerName = event.getOwner().getNome();
        this.maxTickets = event.getMaxTickets();
        this.tipoEvento = event.getTipoEvento();
        this.dataEvento = event.getDataEvento();
        this.indirizzo = event.getIndirizzo();
        if (!event.getImages().isEmpty()) {
            this.immagine1 = (event.getImages().get(0) != null) ? event.getImages().get(0).getData() : new byte[0];
        }
        else immagine1 = null;
        if (event.getImages().size() > 1) {
            this.immagine2 = (event.getImages().get(1) != null) ? event.getImages().get(1).getData() : new byte[0];
        }
        else this.immagine2 = null;
        if (event.getImages().size() > 2) {
            this.immagine3 = (event.getImages().get(2) != null) ? event.getImages().get(2).getData() : new byte[0];
        }
        else this.immagine3 = null;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getMaxTickets() {
        return maxTickets;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public String getDataEvento() {
        return dataEvento;
    }

    public byte[] getImmagine1() {
        return immagine1;
    }

    public byte[] getImmagine2() {
        return immagine2;
    }

    public byte[] getImmagine3() {
        return immagine3;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public Long getId() {
        return id;
    }
}

