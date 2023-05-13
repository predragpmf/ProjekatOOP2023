package pmf.projekatoop.database;

public class UcitavanjeBaze {

    public static BazaPodataka db = new BazaPodataka();

    public static void ucitavanje() {
        db.uspostaviVezu();
        //
        System.out.println("Ucitavanje baze zavrseno!");
    }


}
