# Event Based Systems - Event Generator

Acest proiect conține implementarea generatorului de subscripții/publicații din cadrul temei de laborator, rezolvat integrând thread-uri. Accentul a fost pus momentan pe generarea setului de subscripții, ce respectă cerința privind frecvența fixă (procentajul exact de instanțiere) prin algoritm Fisher-Yates shuffle în loc de distribuție random pură.

## Compilare și Rulare

```bash
# Compilare (din rădăcina proiectului)
javac -d out src/ebs/*.java src/Main.java

# Rulare
java -cp out Main
```

### Output

Se generează câte 1.000.000 de publicații și subscripții, scrise în fișiere text separate pentru scenariul secvențial (`sub_seq_0.txt`, `pub_seq_0.txt`) și cel paralel (`sub_par_0..3.txt`, `pub_par_0..3.txt`, câte 250.000 mesaje per fișier). Timpii de generare sunt afișați în consolă la finalul fiecărei rulări.

---

## Evaluare de Performanță

- **Tip paralelizare:** Threads (Fire de execuție) în memorie partajată partiționată ($N$ mesaje împărțite exact pe $T$ fire). Niciun blocaj (lock) din cauză că fiecare thread scrie independent în propriul său spațiu de fișier temporar alocat blocului.
- **Factorul de paralelism:** Am testat cu $1$ (echivalent execuție secvențială) și cu $4$ threads active.
- **Numărul de mesaje parțial generate:** $1,000,000$ (1 milion de mesaje).
- **Procesoare testate:** AMD Ryzen 5 6600HS (3.30 GHz), AMD Ryzen 7 8845HS (3.80 GHz).

### Timpi Obținuți:

| Procesor | Tip | 1 thread | 4 threads |
|---|---|---|---|
| AMD Ryzen 5 6600HS (3.30 GHz) | Subscripții | ~840 ms | ~441 ms |
| AMD Ryzen 5 6600HS (3.30 GHz) | Publicații | — | — |
| AMD Ryzen 7 8845HS (3.80 GHz) | Subscripții | ~564 ms | ~257 ms |
| AMD Ryzen 7 8845HS (3.80 GHz) | Publicații | ~415 ms | ~274 ms |

Paralelizarea obține performanțe optime și limitează bottleneck-urile tipice multi-threading-ului prin izolarea corectă a procesului independent pentru zone diferite de `start/end index`.
