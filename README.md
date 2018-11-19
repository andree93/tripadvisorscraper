# tripadvisorscraper
Esempio di programma scritto in linguaggio Java, per estrapolare informazioni da tripadvisor, sui ristoranti/bar presenti in una zona, a partire da un link di una pagina di ricerca del sito tripadvisor.it (o .com)
Il programma sfrutta la libreria opensource Jsoup, che è necessario importare per la compilazione. La si puo scaricare da https://jsoup.org/ .
Non ho sviluppato una GUI, ed è disponibile solo la lingua italiana (ma funziona anche con domini di altre nazioni).
Per utilizzarlo è necessario avviare il file jar mediante il comando (dal prompt dei comandi) java -jar nomejar.jar e inserire il link della prima pagina dei risultati di ricerca della località desiderata del sito tripadvisor.it. Il link dev'essere nel formato "https://www.tripadvisor.it/Restaurants-g******-"
In seguito sarà possibile decidere il nome del file csv, dove verranno salvati i risultati
