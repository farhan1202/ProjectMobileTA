package com.dev.projectta.home.model;

import java.util.List;

public class Result {

    /**
     * status : 200
     * message : Success Fetch Data
     * data : [{"nobp_candidate":"1701081000","jumlah_suara":"0","nama":"Kevin Tanes","jurusan":"Teknik Sipil"},{"nobp_candidate":"1701081001","jumlah_suara":"0","nama":"Muhammad Lutfi","jurusan":"Teknik Electro"},{"nobp_candidate":"1701081002","jumlah_suara":"3","nama":"Jefri ","jurusan":"Teknik Electro"}]
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
         * nobp_candidate : 1701081000
         * jumlah_suara : 0
         * nama : Kevin Tanes
         * jurusan : Teknik Sipil
         */

        private String nobp_candidate;
        private String jumlah_suara;
        private String nama;
        private String jurusan;

        public String getNobp_candidate() {
            return nobp_candidate;
        }

        public void setNobp_candidate(String nobp_candidate) {
            this.nobp_candidate = nobp_candidate;
        }

        public String getJumlah_suara() {
            return jumlah_suara;
        }

        public void setJumlah_suara(String jumlah_suara) {
            this.jumlah_suara = jumlah_suara;
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
    }
}
