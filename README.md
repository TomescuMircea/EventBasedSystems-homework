# Event Based Systems - Event Generator

Acest proiect conține implementarea generatorului de subscripții/publicații din cadrul temei de laborator, rezolvat integrând thread-uri. Accentul a fost pus momentan pe generarea setului de subscripții, ce respectă cerința privind frecvența fixă (procentajul exact de instanțiere) prin algoritm Fisher-Yates shuffle în loc de distribuție random pură.

## Evaluare de Performanță

- **Tip paralelizare:** Threads (Fire de execuție) în memorie partajată partiționată ($N$ mesaje împărțite exact pe $T$ fire). Niciun blocaj (lock) din cauză că fiecare thread scrie independent în propriul său spațiu de fișier temporar alocat blocului.
- **Factorul de paralelism:** Am testat cu $1$ (echivalent execuție secvențială) și cu $4$ threads active.
- **Numărul de mesaje parțial generate:** $1,000,000$ (1 milion de mesaje).
- **Procesor Curent:** AMD Ryzen 5 6600HS Creator Edition (3.30 GHz).

### Timpi Obținuți:
- **Test Secvențial (1 thread, factor 1x):** `~840 ms`
- **Test Paralelizat (4 threads, factor 4x):** `~441 ms`

Paralelizarea obține performanțe optime și limitează bottleneck-urile tipice multi-threading-ului prin izolarea corectă a procesului independent pentru zone diferite de `start/end index`.
