package mypipe.kafka

import mypipe.api.Mutation
import mypipe.avro.schema.AvroSchemaUtils

import scala.reflect.runtime.universe._
import mypipe.avro.{ InsertMutation, UpdateMutation, DeleteMutation }

import mypipe.kafka.KafkaGenericMutationAvroConsumer._

object KafkaGenericMutationAvroConsumer {
  type GenericInsertMutationCallback = (InsertMutation) ⇒ Boolean
  type GenericUpdateMutationCallback = (UpdateMutation) ⇒ Boolean
  type GenericDeleteMutationCallback = (DeleteMutation) ⇒ Boolean
}

abstract class KafkaGenericMutationAvroConsumer[SchemaId](
  topic: String,
  zkConnect: String,
  groupId: String,
  schemaIdSizeInBytes: Int)(insertCallback: GenericInsertMutationCallback,
                            updateCallback: GenericUpdateMutationCallback,
                            deleteCallback: GenericDeleteMutationCallback)

    extends KafkaMutationAvroConsumer[mypipe.avro.InsertMutation, mypipe.avro.UpdateMutation, mypipe.avro.DeleteMutation, SchemaId](
      topic, zkConnect, groupId, schemaIdSizeInBytes)(
      insertCallback, updateCallback, deleteCallback,
      implicitly[TypeTag[InsertMutation]],
      implicitly[TypeTag[UpdateMutation]],
      implicitly[TypeTag[DeleteMutation]]) {
}

