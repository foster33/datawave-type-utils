package datawave.data.type;

import java.util.List;

import datawave.data.normalizer.Normalizer;
import datawave.data.normalizer.OneToManyNormalizer;
import datawave.data.type.util.Geometry;
import datawave.data.type.util.TypePrettyNameSupplier;

/**
 * Provides inclusive support for all geometry types. OneToManyNormalizer support is needed as lines and polygons are likely to produce multiple normalized
 * values during ingest.
 */
public class GeometryType extends AbstractGeometryType<Geometry> implements OneToManyNormalizerType<Geometry>, TypePrettyNameSupplier {
    
    protected List<String> normalizedValues;
    private static final String DATA_DICTIONARY_TYPE_NAME = "Geometry";
    
    public GeometryType() {
        super(Normalizer.GEOMETRY_NORMALIZER);
    }
    
    public List<String> normalizeToMany(String in) {
        return ((OneToManyNormalizer<Geometry>) normalizer).normalizeToMany(in);
    }
    
    public void setNormalizedValues(List<String> normalizedValues) {
        this.normalizedValues = normalizedValues;
        setNormalizedValue(this.normalizedValues.toString());
    }
    
    @Override
    public void normalizeAndSetNormalizedValue(Geometry valueToNormalize) {
        setNormalizedValues(((OneToManyNormalizer<Geometry>) normalizer).normalizeDelegateTypeToMany(valueToNormalize));
    }
    
    public List<String> getNormalizedValues() {
        return normalizedValues;
    }
    
    @Override
    public boolean expandAtQueryTime() {
        return false;
    }
    
    @Override
    public String getDataDictionaryTypeValue() {
        return DATA_DICTIONARY_TYPE_NAME;
    }
}
