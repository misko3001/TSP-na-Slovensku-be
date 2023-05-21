package mmojzis.zp.slovak_tsp.controller.mapper;

import io.jenetics.util.ISeq;
import mmojzis.zp.slovak_tsp.controller.dto.matrix.MatrixPointDto;
import mmojzis.zp.slovak_tsp.controller.dto.slovak.SlovakResultDto;
import mmojzis.zp.slovak_tsp.controller.dto.slovak.SlovakTSPRequestDto;
import mmojzis.zp.slovak_tsp.domain.MatrixPoint;
import mmojzis.zp.slovak_tsp.domain.SlovakResult;
import mmojzis.zp.slovak_tsp.service.request.SlovakTSPRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = GeneralMapper.class)
public interface SlovakMapper {

    SlovakResultDto resultToDto(SlovakResult result);

    SlovakTSPRequest dtoToRequest(SlovakTSPRequestDto request);

    default ISeq<MatrixPoint> pointsToISeq(List<MatrixPointDto> points) {
        ISeq<MatrixPoint> iSeq = points.stream().map(this::dtoToMatrixPoint).collect(ISeq.toISeq());
        for (int i = 0; i < iSeq.length(); i++) iSeq.get(i).setIndex(i);
        return iSeq;
    }

    MatrixPoint dtoToMatrixPoint(MatrixPointDto dto);
}
