
/**
  * Created by Guy Guillaume on 4/5/2017.
  * Copyright Guillaume Guy
  */


package controllers

import akka.stream.scaladsl.{FileIO,Source}
import akka.util.ByteString
import play.api.http.HttpEntity
import play.api.mvc._


class DocController extends Controller {


  def index = Action {

    println("path:",new java.io.File(".").getCanonicalPath())
    println("marker")

    val file = new java.io.File("assets/DummyDoc.pdf")
    val path: java.nio.file.Path = file.toPath
    val source: Source[ByteString, _] =  FileIO.fromPath(path)

    Result(
      header = ResponseHeader(200, Map.empty),
      body = HttpEntity.Streamed(source, Some(file.length), Some("application/pdf"))
    )
  }
}
