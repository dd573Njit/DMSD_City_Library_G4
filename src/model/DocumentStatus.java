package model;

import java.util.Date;

public class DocumentStatus {
    private String docID;
    private String title;
    private String copyNo;
    private Date reservedDate;
    private Date borrowedDate;
    private Date returnedDate;

     public DocumentStatus(String docID, String title, String copyNo, Date reservedDate, Date borrowedDate, Date returnedDate) {
         this.docID = docID;
         this.title = title;
         this.copyNo = copyNo;
         this.reservedDate = reservedDate;
         this.borrowedDate = borrowedDate;
         this.returnedDate = returnedDate;
     }

     @Override
    public String toString() {
         String status = "Available";
         if(reservedDate != null) {
             status = "Reserved";
         }
         if(borrowedDate != null) {
             status = "Borrowed";
         }
         if(returnedDate != null) {
             status = "Available";
         }
         return "DOCID: " + docID + " TITLE: " + title + " COPYNO: " + copyNo + " STATUS: " + status;
     }
}
