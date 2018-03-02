package ensemble;

import ensemble.generated.Samples;
import ensemble.util.FeatureChecker;
//保存界面显示目录
public class SampleCategory {
    public final String name;
    /* samples contained in this category directly */
    public final SampleInfo[] samples;
    /* samples contained in this category directly and all sub categories recursively */
    public final SampleInfo[] samplesAll;
    public final SampleCategory[] subCategories;

    public SampleCategory(String name, SampleInfo[] samples, SampleInfo[] samplesAll, SampleCategory[] subCategories) {
        this.name = name;
        this.samples = FeatureChecker.filterSamples(samples);
        this.samplesAll = FeatureChecker.filterSamples(samplesAll);
        this.subCategories = FeatureChecker.filterEmptyCategories(subCategories);
    }

    public SampleInfo sampleForPath(String path) {
        if (path.charAt(0) == '/') { // absolute path
            return Samples.ROOT.sampleForPath(path.split("/"),1);
        } else {
            return sampleForPath(path.split("/"),0);
        }
    }

    private SampleInfo sampleForPath(String[] pathParts, int index) {
        String part = pathParts[index];
        if (samples!=null) for (SampleInfo sample: samples) {
            if (sample.name.equals(part)) return sample;
        }
        if (subCategories!=null) for (SampleCategory category: subCategories) {
            if (category.name.equals(part)) return category.sampleForPath(pathParts, index + 1);
        }
        return null;
    }

    @Override public String toString() {
        return name;
    }
}
