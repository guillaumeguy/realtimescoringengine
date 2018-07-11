package executionrules

import datamodel.Format.RuleInputFormat

/**
  * Created by Guy Guillaume on 4/6/2017.
  */
trait ExecutionRule {

  val ruleName:String = "Rule_" + ID

  // Unique Identifier of the rule
  def ID: Int = isTriggered.toString.hashCode()

  // List of fields used
  def criteria: List[String]

  // Does the rule hit ?
  def isTriggered: List[String] => Boolean

  // Should the rule be triggered
  def isApplicable : Boolean

  // Rules that need to be triggered before this
  def predecessors : List[ExecutionRule]


}
