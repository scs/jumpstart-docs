![scs-logo](cmake/latex/logos/scs_logo_text.png)

jumpstart-docs
==============

<p align="center">
  <a href="https://github.com/scs/jumpstart-docs/actions/workflows/document-compilation.yml?query=branch%3Amain+">
    <img src="https://github.com/scs/jumpstart-docs/actions/workflows/document-compilation.yml/badge.svg?branch=main" alt="Document compilation">
  </a>
  <a href="https://github.com/scs/jumpstart-docs/actions/workflows/java-exercise.yml?query=branch%3Amain+">
    <img src="https://github.com/scs/jumpstart-docs/actions/workflows/java-exercise.yml/badge.svg?branch=main" alt="Java exercise">
  </a>
</p>

Die SCS Jumpstart-Kurse bieten einen effizienten Einstieg in den praktischen Alltag eines Entwicklers.

Dieses Repo beinhaltet unter [topics](topics) alle Unterlagen der unterschiedlichen Kurse.
Die generierten PDFs und den Code kann man als Artefakt der Github Actions herunterladen:
 [Github action runs](https://github.com/scs/jumpstart-docs/actions?query=workflow%3Acompilation+branch%3Amain+)

Die benötigten Tools und Accounts um am Kurs teilzunehmen sind unter [Vorbereitung](topics/admin/introduction.md#vorbereitung)
aufgelistet.

Contributing
-------------

Die Unterlagen sind in `markdown` (und `latex`) geschrieben
und werden mithilfe von `pandoc` und weiteren Tools zu PDFs kompiliert.
[docmake](https://github.com/langchr86/docmake) hilft mit der Integration in cmake.

Falls externe Ressourcen verwendet werden, müssen die korrekt zitiert werden.
Als Bilder von externen Ressourcen werden nur Bilder mit einer Creative Commons Lizenz
verwendet. Die korrekte Verwendung ist hier beschrieben: [Creative Commons - Use & Remix](https://creativecommons.org/use-remix/)
