package mmojzis.zp.slovak_tsp.controller.mapper;

import static mmojzis.zp.slovak_tsp.common.problem.MatrixTSP.INVALID;

import io.jenetics.util.ISeq;
import mmojzis.zp.slovak_tsp.controller.dto.matrix.MatrixPointDto;
import mmojzis.zp.slovak_tsp.controller.dto.matrix.MatrixResultDto;
import mmojzis.zp.slovak_tsp.controller.dto.matrix.MatrixTSPRequestDto;
import mmojzis.zp.slovak_tsp.domain.MatrixPoint;
import mmojzis.zp.slovak_tsp.domain.MatrixResult;
import mmojzis.zp.slovak_tsp.service.request.MatrixTSPRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring", uses = GeneralMapper.class)
public interface MatrixMapper {

    MatrixResultDto resultToDto(MatrixResult result);

    MatrixTSPRequest matrixDtoToRequest(MatrixTSPRequestDto dto);

    MatrixPoint dtoToMatrixPoint(MatrixPointDto dto);

    default ISeq<MatrixPoint> pointListToISeq(List<MatrixPointDto> points) {
        ISeq<MatrixPoint> iSeq = points.stream().map(this::dtoToMatrixPoint).collect(ISeq.toISeq());
        for (int i = 0; i < iSeq.length(); i++) iSeq.get(i).setIndex(i);
        return iSeq;
    }

    @AfterMapping
    default void checkIfPointsAreValid(@MappingTarget MatrixTSPRequest request) {
        ISeq<MatrixPoint> points = request.getPoints();

        long distinct = points.stream().map(MatrixPoint::getName).distinct().count();
        if (distinct < points.stream().count()) {
            throw new IllegalArgumentException("Some points do not have unique names");
        }

        Double[][] distances = request.getDistances();
        for (int i = 0; i < distances.length; i++) {
            if (Arrays.stream(distances[i]).allMatch(dist -> dist.equals(INVALID))) {
                throw new IllegalStateException("Point " + points.get(i).getName() + " is inaccessible");
            }
        }
    }
}
