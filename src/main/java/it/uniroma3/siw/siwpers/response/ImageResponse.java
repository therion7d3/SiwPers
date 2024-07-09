package it.uniroma3.siw.siwpers.response;

public class ImageResponse {
    private final Long id;
    private final byte[] data;
    private final Long ricettaId;
    private final Long userId;

    public ImageResponse(Long id, byte[] data, Long ricettaId, Long userId) {
        this.id = id;
        this.data = data;
        this.ricettaId = ricettaId;
        this.userId = userId;
    }

    public Long getId() {
        return this.id;
    }

    public byte[] getTitle() {
        return this.data;
    }

    public Long getRicettaId() {
        return this.ricettaId;
    }

    public Long getUserId() {
        return this.userId;
    }
}
