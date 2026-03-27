# Event Based Systems - Event Generator

Acest proiect conține implementarea generatorului de subscripții/publicații din cadrul temei de laborator, rezolvat integrând thread-uri. Accentul a fost pus momentan pe generarea setului de subscripții, ce respectă cerința privind frecvența fixă (procentajul exact de instanțiere) prin algoritm Fisher-Yates shuffle în loc de distribuție random pură.

## Evaluare de Performanță

- **Tip paralelizare:** Threads (Fire de execuție) în memorie partajată partiționată ($N$ mesaje împărțite exact pe $T$ fire). Niciun blocaj (lock) din cauză că fiecare thread scrie independent în propriul său spațiu de fișier temporar alocat blocului.
- **Factorul de paralelism:** Am testat cu $1$ (echivalent execuție secvențială) și cu $4$ threads active.
- **Numărul de mesaje parțial generate:** $1,000,000$ (1 milion de mesaje).
- **Procesoare testate:** AMD Ryzen 5 6600HS (3.30 GHz), AMD Ryzen 7 8845HS (3.80 GHz).

### Timpi Obținuți:

| Procesor | 1 thread | 4 threads |
|---|---|---|
| AMD Ryzen 5 6600HS (3.30 GHz) | ~840 ms | ~441 ms |
| AMD Ryzen 7 8845HS (3.80 GHz) | ~500 ms | ~281 ms |

Paralelizarea obține performanțe optime și limitează bottleneck-urile tipice multi-threading-ului prin izolarea corectă a procesului independent pentru zone diferite de `start/end index`.
