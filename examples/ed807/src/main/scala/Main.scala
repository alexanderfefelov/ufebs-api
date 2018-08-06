import com.github.alexanderfefelov.ufebs.api._
import better.files.Dsl.SymbolicOperations
import better.files.File

import scala.xml.XML

object Main extends App {

  if (args.length < 1) {
    println("You must specify the path to .xml file as a command-line argument")
    System.exit(1)
  }

  val ed807Xml = args(0)

  val rawXml = XML.loadFile(ed807Xml)
  val ed807 = scalaxb.fromXML[ED807](rawXml)

  val out = File("out.txt")

  out <
    s"""
      |#------------------------------------------------------------
      |# EDNo: ${ed807.EDNo}
      |# EDDate: ${ed807.EDDate}
      |# CreationDateTime: ${ed807.CreationDateTime}
      |# Total entries: ${ed807.BICDirectoryEntry.size}
      |#------------------------------------------------------------
      |
      |""".stripMargin

  for (e <- ed807.BICDirectoryEntry) {
    out << s"БИК ${e.BIC} с ${e.ParticipantInfo.DateIn} по ${e.ParticipantInfo.DateOut.getOrElse("---")}"
    out << s"    ${e.ParticipantInfo.NameP}, тип: ${e.ParticipantInfo.ParticipantStatus.getOrElse("---")}"
    out << s"    ${e.ParticipantInfo.Ind.getOrElse("---")}, ${e.ParticipantInfo.Tnp.getOrElse("---")} ${e.ParticipantInfo.Nnp.getOrElse("---")}, ${e.ParticipantInfo.Adr.getOrElse("---")}"
    for (a <- e.Accounts) {
      out << s"    Счёт: ${a.Account} с ${a.DateIn} по ${a.DateOut.getOrElse("---")}, тип: ${a.RegulationAccountType}, статус: ${a.AccountStatus.getOrElse("---")}"
    }
  }

}
