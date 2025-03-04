# RPG-CORE

## Opis projektu
RPG-CORE to kompleksowy plugin RPG dla serwerów Minecraft, oferujący system klas postaci, system gildii, rozwój ekwipunku, kopalnie, misje oraz interakcję z NPC. Jest to w pełni samodzielne rozwiązanie, które nie wymaga MySQL ani Redis – wszystkie dane przechowywane są w pamięci serwera, a konfiguracje generowane są automatycznie na podstawie klas konfiguracyjnych.

## Funkcjonalności
- **System klas postaci** – gracz może wybierać między różnymi klasami, np. wojownik, łowca, każda z unikalnymi statystykami i ekwipunkiem.
- **Gildie** – możliwość tworzenia, dołączania i zarządzania gildiami z poziomu komend.
- **Kopalnie** – system dynamicznych kopalń z regenerującymi się rudami.
- **Mapa ekspowisk** – predefiniowane lokacje dla graczy do zdobywania doświadczenia.
- **NPC i zadania** – NPC oferują misje, handel i interakcję z graczem.
- **System dropów** – specjalne przedmioty z przeciwników i skrzynek.
- **System ekonomii** – możliwość sprzedaży przedmiotów NPC w zamian za wirtualną walutę.
- **Zarządzanie ekwipunkiem** – customowe przedmioty z unikalnymi atrybutami.
- **Interfejs czatu** – dostosowywane formaty wiadomości dla globalnego czatu i gildii.
- **System rang** – mechanizm nadawania rang z określonymi przywilejami.
- **Dynamiczne BossBary i Sidebar** – dodatkowe informacje o stanie gry na paskach HUD.
- **System anty-logout** – zabezpieczenie przed ucieczką z walki.

## Struktura projektu
### **1. Główne moduły**
- **`bossbar`** – zarządzanie BossBarami na ekranie graczy.
- **`chat`** – system czatu oraz moderacja wiadomości.
- **`commands`** – obsługa komend dla graczy i administratorów.
- **`guilds`** – mechanika gildii oraz interakcja graczy.
- **`items`** – niestandardowe przedmioty RPG z atrybutami.
- **`listeners`** – obsługa zdarzeń gracza, np. interakcje z NPC.
- **`managers`** – centralne zarządzanie danymi graczy i konfiguracją.
- **`maps`** – predefiniowane mapy do expienia i walk.
- **`mine`** – system kopalń i regenerujących się surowców.
- **`mysterybox`** – skrzynki z nagrodami dla graczy.
- **`npcs`** – interakcja z NPC, w tym system questów.
- **`ranks`** – zarządzanie rangami graczy.
- **`sidebar`** – pasek informacji wyświetlany graczom.
- **`storage`** – przechowywanie danych o graczach i przedmiotach.
- **`trades`** – system handlu i kupców NPC.
- **`util`** – funkcje pomocnicze.

### **2. Przykładowa klasa konfiguracyjna**
Konfiguracja tworzona automatycznie na podstawie klasy:
```java
@ConfigName("rpg.yml")
public interface RPGConfig extends Config {
    @Comment("Standardowa ilość expa za poziom")
    default int getStandardExp() {
        return 250;
    }

    @Comment("Czas trwania anty-logoutu (w sekundach)")
    default int getCombatDuration() {
        return 20;
    }
}
```

## Instalacja i konfiguracja
1. Pobierz plugin i umieść go w katalogu `plugins` serwera Minecraft.
2. Uruchom serwer – plugin automatycznie wygeneruje konfigurację.
3. Dostosuj opcje w plikach `.yml`, które powstały w folderze `config`.
4. Restartuj serwer, aby załadować zmiany.

## Podsumowanie
RPG-CORE to wszechstronne narzędzie do budowy serwerów RPG w Minecraft, zapewniające dynamiczną rozgrywkę i rozbudowane mechaniki. Dzięki automatycznym konfiguracjom, brak konieczności używania zewnętrznych baz danych oraz dużej modularności, plugin stanowi solidne rozwiązanie dla serwerów typu RPG.

## Autorzy
- **CzarnaWoda** – Główny deweloper projektu
