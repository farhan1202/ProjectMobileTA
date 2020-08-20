package com.dev.projectta.home.model;

import java.util.List;

public class Candidate {

    /**
     * status : 200
     * message : Success Fetch Data
     * data : [{"id":"24","nobp_candidate":"1701081001","nama":"Hidayatul Fadhilah","jurusan":"Teknik Komputer","keterangan":"Lorem ipsum dolor sit amet, consectetur adipisicing elit. Consectetur nostrum odio laborum laboriosam iste. Assumenda cupiditate unde, totam placeat nobis dolorum! Autem ratione quia tempora. Aspernatur nemo velit ut ullam.","profile_image":"image/nama1.jpg"},{"id":"25","nobp_candidate":"1701081002","nama":"Rahmat Afif","jurusan":"Teknik Listrik","keterangan":"Lorem ipsum dolor sit amet, consectetur adipisicing elit. Consectetur nostrum odio laborum laboriosam iste. Assumenda cupiditate unde, totam placeat nobis dolorum! Autem ratione quia tempora. Aspernatur nemo velit ut ullam.","profile_image":"image/person2.jpg"}]
     */

    private String status;
    private String message;
    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 24
         * nobp_candidate : 1701081001
         * nama : Hidayatul Fadhilah
         * jurusan : Teknik Komputer
         * keterangan : Lorem ipsum dolor sit amet, consectetur adipisicing elit. Consectetur nostrum odio laborum laboriosam iste. Assumenda cupiditate unde, totam placeat nobis dolorum! Autem ratione quia tempora. Aspernatur nemo velit ut ullam.
         * profile_image : image/nama1.jpg
         */

        private String id;
        private String nobp_candidate;
        private String nama;
        private String jurusan;
        private String keterangan;
        private String profile_image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNobp_candidate() {
            return nobp_candidate;
        }

        public void setNobp_candidate(String nobp_candidate) {
            this.nobp_candidate = nobp_candidate;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getJurusan() {
            return jurusan;
        }

        public void setJurusan(String jurusan) {
            this.jurusan = jurusan;
        }

        public String getKeterangan() {
            return keterangan;
        }

        public void setKeterangan(String keterangan) {
            this.keterangan = keterangan;
        }

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }
    }
}
