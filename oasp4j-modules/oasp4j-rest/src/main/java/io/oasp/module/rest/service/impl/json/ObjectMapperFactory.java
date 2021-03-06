package io.oasp.module.rest.service.impl.json;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * A generic factory to {@link #createInstance() create} instances of a Jackson {@link ObjectMapper}. It allows to
 * configure the {@link ObjectMapper} for polymorphic transfer-objects.
 *
 * @see #setBaseClasses(Class...)
 * @see #setSubtypes(NamedType...)
 *
 * @author hohwille
 * @author agreul
 */
public class ObjectMapperFactory {

  static final String GROUP_ID = "io.oasp.java";

  static final String ARTIFACT_ID = "oasp4j-rest";

  private Class<?>[] baseClasses;

  private List<NamedType> subtypeList;

  private SimpleModule extensionModule;

  /**
   * The constructor.
   */
  public ObjectMapperFactory() {

    super();
  }

  /**
   * Gets access to a generic extension {@link SimpleModule module} for customizations to Jackson JSON mapping.
   *
   * @see SimpleModule#addSerializer(Class, com.fasterxml.jackson.databind.JsonSerializer)
   * @see SimpleModule#addDeserializer(Class, com.fasterxml.jackson.databind.JsonDeserializer)
   *
   * @return extensionModule
   */
  public SimpleModule getExtensionModule() {

    if (this.extensionModule == null) {
      this.extensionModule =
          new SimpleModule("oasp.ExtensionModule", new Version(1, 0, 0, null, GROUP_ID, ARTIFACT_ID));
    }
    return this.extensionModule;
  }

  /**
   * @param baseClasses are the base classes that are polymorphic (e.g. abstract transfer-object classes that have
   *        sub-types). You also need to register all sub-types of these polymorphic classes via
   *        {@link #setSubtypes(NamedType...)}.
   */
  public void setBaseClasses(Class<?>... baseClasses) {

    this.baseClasses = baseClasses;
  }

  /**
   * @see #setSubtypes(NamedType...)
   *
   * @param subtypeList the {@link List} of {@link NamedType}s to register the subtypes.
   */
  public void setSubtypeList(List<NamedType> subtypeList) {

    this.subtypeList = subtypeList;
  }

  /**
   * @param subtypeList the {@link NamedType}s as pair of {@link Class} reflecting a polymorphic sub-type together with
   *        its unique name in JSON format.
   */
  public void setSubtypes(NamedType... subtypeList) {

    setSubtypeList(Arrays.asList(subtypeList));
  }

  /**
   * @return an instance of {@link ObjectMapper} configured for polymorphic resolution.
   */
  public ObjectMapper createInstance() {

    ObjectMapper mapper = new ObjectMapper();

    if ((this.baseClasses != null) && (this.baseClasses.length > 0)) {
      SimpleModule polymorphyModule = new MixInAnnotationsModule(this.baseClasses);
      mapper.registerModule(polymorphyModule);
    }

    if (this.extensionModule != null) {
      mapper.registerModule(this.extensionModule);
    }

    if (this.subtypeList != null) {
      SubtypeResolver subtypeResolver = mapper.getSubtypeResolver();
      for (NamedType subtype : this.subtypeList) {
        subtypeResolver.registerSubtypes(subtype);
      }
      mapper.setSubtypeResolver(subtypeResolver);
    }

    return mapper;
  }

}
