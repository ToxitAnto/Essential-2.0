# ⚡ CkantoCore

<div align="center">

```
 ██████╗██╗  ██╗ █████╗ ███╗   ██╗████████╗ ██████╗      ██████╗ ██████╗ ██████╗ ███████╗
██╔════╝██║ ██╔╝██╔══██╗████╗  ██║╚══██╔══╝██╔═══██╗    ██╔════╝██╔═══██╗██╔══██╗██╔════╝
██║     █████╔╝ ███████║██╔██╗ ██║   ██║   ██║   ██║    ██║     ██║   ██║██████╔╝█████╗  
██║     ██╔═██╗ ██╔══██║██║╚██╗██║   ██║   ██║   ██║    ██║     ██║   ██║██╔══██╗██╔══╝  
╚██████╗██║  ██╗██║  ██║██║ ╚████║   ██║   ╚██████╔╝    ╚██████╗╚██████╔╝██║  ██║███████╗
 ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝   ╚═╝    ╚═════╝      ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝
```

**Il plugin essenziale, reinventato.**

![Version](https://img.shields.io/badge/version-1.0.0-00d4ff?style=for-the-badge&labelColor=0d1117)
![Paper](https://img.shields.io/badge/Paper-1.21.4-f96854?style=for-the-badge&labelColor=0d1117&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUg=)
![Java](https://img.shields.io/badge/Java-17+-f89820?style=for-the-badge&labelColor=0d1117&logo=openjdk)
![Author](https://img.shields.io/badge/author-ckanto-a78bfa?style=for-the-badge&labelColor=0d1117)
![License](https://img.shields.io/badge/license-MIT-22c55e?style=for-the-badge&labelColor=0d1117)

</div>

---

## 〔 Panoramica 〕

**CkantoCore** è un plugin per server Minecraft (**Paper 1.21+**) che sostituisce completamente **EssentialsX** con un'implementazione leggera, moderna e senza dipendenze esterne. Ogni comando è ottimizzato per prestazioni e usabilità, con messaggi completamente configurabili.

---

## 〔 Comandi 〕

<table>
<thead>
<tr>
<th align="center">Comando</th>
<th align="center">Uso</th>
<th align="center">Permesso</th>
<th align="center">Descrizione</th>
</tr>
</thead>
<tbody>
<tr>
<td align="center"><code>/fly</code></td>
<td><code>/fly [giocatore]</code></td>
<td><code>ckanto.fly</code></td>
<td>Attiva/disattiva il volo</td>
</tr>
<tr>
<td align="center"><code>/flyspeed</code></td>
<td><code>/flyspeed &lt;1-10&gt; [giocatore]</code></td>
<td><code>ckanto.flyspeed</code></td>
<td>Imposta la velocità di volo</td>
</tr>
<tr>
<td align="center"><code>/gmc</code></td>
<td><code>/gmc [giocatore]</code></td>
<td><code>ckanto.gmc</code></td>
<td>Modalità Creativa</td>
</tr>
<tr>
<td align="center"><code>/gms</code></td>
<td><code>/gms [giocatore]</code></td>
<td><code>ckanto.gms</code></td>
<td>Modalità Sopravvivenza</td>
</tr>
<tr>
<td align="center"><code>/gmsp</code></td>
<td><code>/gmsp [giocatore]</code></td>
<td><code>ckanto.gmsp</code></td>
<td>Modalità Spettatore</td>
</tr>
<tr>
<td align="center"><code>/tpto</code></td>
<td><code>/tpto &lt;giocatore&gt;</code></td>
<td><code>ckanto.tpto</code></td>
<td>Teleportati da un giocatore a un altro</td>
</tr>
<tr>
<td align="center"><code>/warp</code></td>
<td><code>/warp &lt;nome|set|del|list&gt;</code></td>
<td><code>ckanto.warp</code></td>
<td>Gestisci e usa i warp</td>
</tr>
<tr>
<td align="center"><code>/kit</code></td>
<td><code>/kit &lt;nome|list|create|delete&gt;</code></td>
<td><code>ckanto.kit</code></td>
<td>Gestisci e riscatta i kit</td>
</tr>
</tbody>
</table>

---

## 〔 Permessi 〕

```yaml
ckanto.fly              # Attiva/disattiva il volo su se stessi
ckanto.fly.others       # Attiva/disattiva il volo su altri
ckanto.flyspeed         # Imposta velocità di volo su se stessi
ckanto.flyspeed.others  # Imposta velocità di volo su altri
ckanto.gmc              # Gamemode creative su se stessi
ckanto.gmc.others       # Gamemode creative su altri
ckanto.gms              # Gamemode survival su se stessi
ckanto.gms.others       # Gamemode survival su altri
ckanto.gmsp             # Gamemode spectator su se stessi
ckanto.gmsp.others      # Gamemode spectator su altri
ckanto.tpto             # Teleportarsi verso un giocatore
ckanto.warp             # Usare i warp
ckanto.warp.set         # Creare warp
ckanto.warp.del         # Eliminare warp
ckanto.kit              # Usare i kit
ckanto.kit.create       # Creare kit
ckanto.kit.delete       # Eliminare kit
ckanto.kit.bypass       # Bypassare il cooldown dei kit
```

> Tutti i permessi sopra elencati hanno **default: op**. Assegna i permessi agli utenti tramite un plugin come **LuckPerms**.

---

## 〔 Installazione 〕

```bash
# 1. Scarica il .jar dalla sezione Releases
# 2. Inseriscilo nella cartella /plugins del tuo server
# 3. Avvia o riavvia il server
# 4. Modifica /plugins/CkantoCore/config.yml a piacere
# 5. Usa /reload confirm oppure riavvia
```

**Requisiti:**
- Paper (o fork) `1.21+`
- Java `17+`

---

## 〔 Configurazione 〕

Il file `config.yml` permette di personalizzare ogni aspetto del plugin. Tutti i messaggi supportano i codici colore con `&`.

```yaml
prefix: '&8[&bCkanto&8] &r'

messages:
  no-permission: '&cNon hai il permesso per questo comando.'

fly:
  enabled-self: '&aVolo &2abilitato&a.'
  disabled-self: '&cVolo &4disabilitato&c.'

warp:
  teleported: '&aTeleportato al warp &2{warp}&a.'
  set: '&aWarp &2{warp} &acreato.'

kit:
  claimed: '&aKit &2{kit} &ariscattato.'
  cooldown: '&cDevi aspettare &e{time} &cper &e{kit}&c.'
```

---

## 〔 Struttura File 〕

```
plugins/CkantoCore/
├── config.yml       ← Messaggi e prefisso
├── warps.yml        ← Warp salvati automaticamente
├── kits.yml         ← Kit creati in-game
└── cooldowns.yml    ← Cooldown dei kit per giocatore
```

---

## 〔 Build dal Sorgente 〕

```bash
git clone https://github.com/ckanto/CkantoCore.git
cd CkantoCore
mvn clean package
# Output: target/CkantoCore-1.0.0.jar
```

---

## 〔 Confronto con EssentialsX 〕

| Funzione | EssentialsX | CkantoCore |
|:---|:---:|:---:|
| `/fly` | ✅ | ✅ |
| `/flyspeed` | ✅ | ✅ |
| `/gmc` `/gms` `/gmsp` | ✅ | ✅ |
| `/tpto` | ✅ | ✅ |
| `/warp` | ✅ | ✅ |
| `/kit` | ✅ | ✅ |
| Nessuna dipendenza esterna | ❌ | ✅ |
| Config messaggi completa | ⚠️ parziale | ✅ |
| Leggero e moderno | ❌ | ✅ |

---

<div align="center">

**Sviluppato con ♦ da [ckanto](https://github.com/ckanto)**

*CkantoCore — Essenziale. Leggero. Tuo.*

</div>
